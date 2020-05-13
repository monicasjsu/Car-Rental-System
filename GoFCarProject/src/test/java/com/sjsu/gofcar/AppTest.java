package com.sjsu.gofcar;

import static org.assertj.core.api.Assertions.assertThat;

import com.sjsu.gofcar.controller.AppController;
import com.sjsu.gofcar.controller.LoginController;

import com.sjsu.gofcar.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
public class AppTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private AppController appController;

    @Autowired
    private UserController userController;

    @Autowired
    private App app;

    @Test
    public void contextLoads() {
        assertThat(appController).isNotNull();
        assertThat(loginController).isNotNull();
        assertThat(userController).isNotNull();
    }

    @Test
    public void testCorsFilter() {
        FilterRegistrationBean corsFilter = app.corsFilter();
        assertThat(corsFilter.getOrder()).isEqualTo(0);
        assertThat(corsFilter.getFilter()).isNotNull();
    }

//    @Test
//    public void testUserAuthFilter() {
//        FilterRegistrationBean userAuthFilter = app.registerUserJwtFilterBean();
//        assertThat(userAuthFilter.getUrlPatterns()).containsExactly("/api/user/*");
//    }
//
//    @Test
//    public void testAdminAuthFilter() {
//        FilterRegistrationBean userAuthFilter = app.registerAdminJwtFilterBean();
//        assertThat(userAuthFilter.getUrlPatterns()).containsExactly("/api/admin/*");
//    }
}