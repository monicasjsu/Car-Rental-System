package com.sjsu.gofcar.config;

import static org.junit.Assert.*;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminAuthFilterTest {

  @Autowired
  Environment env;

  private static String USER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb25pLmRAZ21haWwuY29tIiwicm9sZXMiOiJ1c2VyIiwiaWF0IjoxNTg4MzE0NTM0fQ.alkiAle0RXKmWwY-i3LWO66qJWsmVXh5SxYWIUAw5dQ";
  private static String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb25pY2EuYWRtaW5Ac2pzdS5lZHUiLCJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNTg4NzM0NTY0fQ.VF8iJj9qx1_7Onu602X8vdxg2QZW74ixAKDjrTzt2Jw";


  @Test
  public void testUserShouldNotAccessAdminApis() throws IOException, ServletException {
    AdminAuthFilter filter = new AdminAuthFilter(env);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    request.addHeader("Authorization", USER_TOKEN);
    filter.doFilter(request, response, new MockFilterChain());
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    Claims claims = (Claims) request.getAttribute("claims");
    assertThat(claims).isNull();
  }

  @Test
  public void testAdminAuthorizationSuccess() throws IOException, ServletException {
    AdminAuthFilter filter = new AdminAuthFilter(env);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    request.addHeader("Authorization", ADMIN_TOKEN);
    filter.doFilter(request, response, new MockFilterChain());
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    Claims claims = (Claims) request.getAttribute("claims");
    assertThat(claims.getSubject()).isEqualTo("monica.admin@sjsu.edu");
    assertThat(claims.get("roles")).isEqualTo("admin");
  }
}