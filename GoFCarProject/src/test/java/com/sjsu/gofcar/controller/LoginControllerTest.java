package com.sjsu.gofcar.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.gofcar.model.Address;
import com.sjsu.gofcar.model.user.AdminDetails;
import com.sjsu.gofcar.model.user.CreditCard;
import com.sjsu.gofcar.model.user.License;
import com.sjsu.gofcar.model.user.Login;
import com.sjsu.gofcar.model.user.UserDetails;
import com.sjsu.gofcar.respository.AdminDetailsRepository;
import com.sjsu.gofcar.respository.UserDetailsRepository;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private UserDetailsRepository userDetailsRepository;

  @MockBean
  private AdminDetailsRepository adminDetailsRepository;

  private static String USER_TOKEN = "$2a$10$.VVwACMUg.spcEjY1Q8vw.gSFQEfkSWajakkuxcrlcyYn4JuFeEK2";
  private static String ADMIN_TOKEN = "$2a$10$dkZtV79hpFw8zLXxJBZdq.heb0X0dRoMkrD91.eXa9CZXwNN1W996";


  private UserDetails userDetails = UserDetails.builder()
      .firstName("She")
      .lastName("Codes")
      .age(20)
      .password("monica")
      .email("monica.dommaraju@gmail.com")
      .address(Address.builder()
          .city("Sunnyvale")
          .state("CA")
          .streetName("825 E Evyn St")
          .zipCode(94095)
          .build())
      .card(CreditCard.builder()
          .name("SheCodes")
          .cardNumber("1234123412341234")
          .cvv(123)
          .expiryDate(new Date(2022, 12, 12))
          .dateOfIssue(new Date(2018, 12, 12))
          .build())
      .license(License.builder()
          .stateOfIssue("CA")
          .licenseId("12345")
          .type("motor")
          .expiry(new Date(2022, 12, 12))
          .build())
      .isActive(true)
      .build();

  private Login userLogin = Login.builder()
      .password("monica")
      .email("monica.dommaraju@gmail.com")
      .build();

  private AdminDetails adminDetails = AdminDetails.builder()
      .firstName("SheCodes")
      .lastName("Admin")
      .password("monica")
      .email("monica.admin@sjsu.edu")
      .build();

  private Login adminLogin = Login.builder()
      .password("monica")
      .email("monica.admin@sjsu.edu")
      .build();

  @Test
  public void register() throws Exception {
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.empty());

    when(userDetailsRepository.save(any(UserDetails.class)))
        .thenReturn(userDetails.toBuilder()
            .isActive(false)
            .build()
        );

    this.mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userDetails)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(
            mapper.writeValueAsString(userDetails.toBuilder()
            .password(null)
            .isActive(false)
            .build()
        )));
  }

  @Test
  public void registerDuplicateEmail() throws Exception {
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetails));

    this.mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userDetails)))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void registerAdmin() throws Exception {
    when(adminDetailsRepository.findById(anyString()))
        .thenReturn(Optional.empty());

    when(adminDetailsRepository.save(any(AdminDetails.class)))
        .thenReturn(adminDetails);

    this.mockMvc.perform(post("/admin/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(adminDetails)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(
            mapper.writeValueAsString(adminDetails.toBuilder()
                .password(null)
                .build()
            )));
  }

  @Test
  public void registerDuplicateAdminEmail() throws Exception {
    when(adminDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(adminDetails));

    this.mockMvc.perform(post("/admin/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(adminDetails)))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void login() throws Exception {
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetails.toBuilder()
            .password(USER_TOKEN)
            .build()
        ));

    this.mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userLogin)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void loginInactiveUser() throws Exception {
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetails.toBuilder()
            .isActive(false)
            .password(USER_TOKEN)
            .build())
        );

    this.mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userLogin)))
        .andDo(print())
        .andExpect(status().isExpectationFailed());
  }

  @Test
  public void loginInvalidPass() throws Exception {
    when(userDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(userDetails.toBuilder()
            .password("1234")
            .build())
        );

    this.mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userLogin)))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void loginAdmin() throws Exception {
    when(adminDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(adminDetails.toBuilder()
            .password(ADMIN_TOKEN)
            .build()
        ));

    this.mockMvc.perform(post("/admin/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(adminLogin)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void loginAdminInvalidPass() throws Exception {
    when(adminDetailsRepository.findById(anyString()))
        .thenReturn(Optional.of(adminDetails.toBuilder()
            .password("1234")
            .build())
        );

    this.mockMvc.perform(post("/admin/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(adminLogin)))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }
}