package io.brunoonofre64.infrastructure.config.security;

import io.brunoonofre64.infrastructure.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.brunoonofre64.infrastructure.config.security.ConstantVariables.*;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userService;

   private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
             http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(WEB_REQUEST_V1_CUSTOMER)
                   .permitAll()
                .antMatchers(WEB_REQUEST_V1_EMPLOYEE)
                  .hasAnyRole(ADMIN, MANAGER)
                .antMatchers(WEB_REQUEST_V1_ORDER)
                  .hasAnyRole(ADMIN, MANAGER)
                .antMatchers(WEB_REQUEST_V1_PRODUCT)
                  .hasAnyRole(ADMIN, MANAGER, EMPLOYEE)
                .antMatchers(WEB_REQUEST_V1_USER)
                  .hasAnyRole(ADMIN)
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
