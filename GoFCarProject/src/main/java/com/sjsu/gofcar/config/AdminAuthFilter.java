package com.sjsu.gofcar.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

public class AdminAuthFilter extends GenericFilterBean {
  private static String AUTHORIZATION = "Authorization";
  private static String BEARER_PREFIX = "Bearer ";

  private Environment env;

  public AdminAuthFilter(Environment env) {
    this.env = env;
  }

  public void doFilter(
      ServletRequest req,
      ServletResponse res,
      FilterChain chain
  ) throws IOException, ServletException {

    final HttpServletRequest request = (HttpServletRequest) req;
    final HttpServletResponse response = (HttpServletResponse) res;

    final String authorizationHeader = request.getHeader(AUTHORIZATION);

    if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write( "Invalid Auth Bearer Token");
      return;
    }

    final String token = authorizationHeader.split(" ")[1];
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(env.getProperty("jwt.secret"))
          .parseClaimsJws(token)
          .getBody();
      if (claims.get("roles").equals("user")) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write( "Unauthorized to access admin apis");
        return;
      }
      request.setAttribute("claims", claims);
    } catch (final SignatureException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write( "Invalid Auth Token");
      return;
    }
    chain.doFilter(req, res);
  }
}
