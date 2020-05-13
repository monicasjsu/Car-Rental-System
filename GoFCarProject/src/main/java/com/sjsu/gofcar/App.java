package com.sjsu.gofcar;
import com.sjsu.gofcar.config.AdminAuthFilter;
import com.sjsu.gofcar.config.UserAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class App {

  @Autowired
  private Environment env;

  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }

  @Bean
  public FilterRegistrationBean registerUserJwtFilterBean() {
    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new UserAuthFilter(env));
    filterBean.addUrlPatterns("/api/user/*");
    return filterBean;
  }

  @Bean
  public FilterRegistrationBean registerAdminJwtFilterBean() {
    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new AdminAuthFilter(env));
    filterBean.addUrlPatterns("/api/admin/*");
    return filterBean;
  }

  @Bean
  public BCryptPasswordEncoder registerPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public static void main( String[] args ) {
    System.out.println("Inside Application class - GOFCAR");
    SpringApplication.run(App.class, args);
  }
}
