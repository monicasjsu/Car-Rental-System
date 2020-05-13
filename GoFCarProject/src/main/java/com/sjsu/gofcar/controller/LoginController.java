package com.sjsu.gofcar.controller;

import com.sjsu.gofcar.interfaces.ILoginController;
import com.sjsu.gofcar.model.user.AdminDetails;
import com.sjsu.gofcar.model.user.Login;
import com.sjsu.gofcar.model.user.UserDetails;
import com.sjsu.gofcar.respository.AdminDetailsRepository;
import com.sjsu.gofcar.respository.UserDetailsRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
public class LoginController implements ILoginController {

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Autowired
  AdminDetailsRepository adminDetailsRepository;

  @Autowired
  private Environment env;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/register")
  @Override
  public @ResponseBody UserDetails register(@RequestBody UserDetails userDetails) {
    if (userDetailsRepository.findById(userDetails.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exists!");
    }
    userDetails.setIsActive(false);
    userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
    UserDetails ud = userDetailsRepository.save(userDetails);
    ud.setPassword(null);
    return ud;
  }

  @PostMapping("/admin/register")
  @Override
  public @ResponseBody AdminDetails registerAdmin(@RequestBody AdminDetails adminDetails) {
    if (adminDetailsRepository.findById(adminDetails.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exists!");
    }
    adminDetails.setPassword(passwordEncoder.encode(adminDetails.getPassword()));
    AdminDetails ad = adminDetailsRepository.save(adminDetails);
    ad.setPassword(null);
    return ad;
  }

  @PostMapping("/login")
  @Override
  public @ResponseBody String login(@RequestBody Login login) {
    if (isInValidLogin(login)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(login.getEmail());

    if (!userDetailsOpt.isPresent()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
    }

    UserDetails userDetails = userDetailsOpt.get();

    boolean isValidPassword = passwordEncoder.matches(login.getPassword(), userDetails.getPassword());
    if (!isValidPassword) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    if (!userDetails.getIsActive()) {
      throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "You registration is still in progress. Please try later");
    }

    return Jwts.builder()
        .setSubject(login.getEmail())
        .claim("roles", "user")
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS256, env.getProperty("jwt.secret"))
        .compact();
  }

  @PostMapping("/admin/login")
  @Override
  public String loginAdmin(@RequestBody Login login) {
    if (isInValidLogin(login)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    Optional<AdminDetails> adminDetailsOpt = adminDetailsRepository.findById(login.getEmail());

    if (!adminDetailsOpt.isPresent()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin not found");
    }

    AdminDetails adminDetails = adminDetailsOpt.get();

    boolean isValidPassword = passwordEncoder.matches(login.getPassword(), adminDetails.getPassword());
    if (!isValidPassword) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    return Jwts.builder()
        .setSubject(login.getEmail())
        .claim("roles", "admin")
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS256, env.getProperty("jwt.secret"))
        .compact();
  }

  private boolean isInValidLogin(Login login) {
    return login == null ||
        StringUtils.isEmpty(login.getEmail()) ||
        StringUtils.isEmpty(login.getPassword());
  }
}
