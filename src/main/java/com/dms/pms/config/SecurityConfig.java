package com.dms.pms.config;

import com.dms.pms.security.JwtConfigurer;
import com.dms.pms.security.JwtTokenProvider;
import com.dms.pms.security.auth.RoleType;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/user").hasAuthority(RoleType.USER.toString())
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/user").permitAll()
                .antMatchers(HttpMethod.POST, "/student/**").hasAuthority(RoleType.ADMIN.toString())
                .antMatchers("/user/student").hasAuthority(RoleType.USER.toString())
                .antMatchers("/auth/**").permitAll()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}