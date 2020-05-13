package com.sjsu.gofcar.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAuthFilterTest {

  @Autowired
  Environment env;

  private static String USER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb25pLmRAZ21haWwuY29tIiwicm9sZXMiOiJ1c2VyIiwiaWF0IjoxNTg4MzE0NTM0fQ.alkiAle0RXKmWwY-i3LWO66qJWsmVXh5SxYWIUAw5dQ";
  private static String USER_INVALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb25pLmRAZ21haWwuY29tIiwicm9sZXMiOiJ1c2VyIiwiaWF0IjoxNTg4MzE0NTM0fQ.alkiAle0RXKmWwY-i3LWO66qJWsmVXh5SxYWIUAw123";


  @Test
  public void testUserInvalidToken() throws IOException, ServletException {
    UserAuthFilter filter = new UserAuthFilter(env);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    request.addHeader("Authorization", USER_INVALID_TOKEN);
    filter.doFilter(request, response, new MockFilterChain());
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    Claims claims = (Claims) request.getAttribute("claims");
    assertThat(claims).isNull();
  }

  @Test
  public void testUserValidToken() throws IOException, ServletException {
    UserAuthFilter filter = new UserAuthFilter(env);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    request.addHeader("Authorization", USER_TOKEN);
    filter.doFilter(request, response, new MockFilterChain());
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    Claims claims = (Claims) request.getAttribute("claims");
    assertThat(claims.getSubject()).isEqualTo("moni.d@gmail.com");
    assertThat(claims.get("roles")).isEqualTo("user");
  }
}