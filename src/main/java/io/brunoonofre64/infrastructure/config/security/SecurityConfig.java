package io.brunoonofre64.infrastructure.config.security;

import io.brunoonofre64.infrastructure.config.security.ConstantVariables.WEB_REQUEST;
import io.brunoonofre64.infrastructure.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.brunoonofre64.infrastructure.config.security.ConstantVariables.*;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(WEB_REQUEST.V1_CUSTOMER)
                   .permitAll()
                .antMatchers(WEB_REQUEST.V1_EMPLOYEE)
                  .hasAnyRole(ADMIN, MANAGER)
                .antMatchers(WEB_REQUEST.V1_ORDER)
                  .hasAnyRole(ADMIN, MANAGER)
                .antMatchers(WEB_REQUEST.V1_PRODUCT)
                  .hasAnyRole(ADMIN, MANAGER, EMPLOYEE)
                .antMatchers(WEB_REQUEST.V1_USER)
                  .hasAnyRole(ADMIN)
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
