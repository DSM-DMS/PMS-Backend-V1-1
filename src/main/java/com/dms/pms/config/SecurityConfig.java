package com.dms.pms.config;

import com.dms.pms.security.JwtConfigurer;
import com.dms.pms.security.JwtTokenProvider;
import com.dms.pms.security.auth.RoleType;
import com.dms.pms.security.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

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
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, "/user").hasAuthority(RoleType.USER.toString())
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/user").permitAll()
                .antMatchers(HttpMethod.POST, "/student/**").hasAuthority(RoleType.ADMIN.toString())
                .antMatchers("/user/student").hasAuthority(RoleType.USER.toString())
                // Swagger
                .antMatchers("/swagger-ui/").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                .and()
                    .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("${spring.security.oauth2.client.registration.google.clientId}") String googleClientId,
            @Value("${spring.security.oauth2.client.registration.google.clientSecret}") String googleClientSecret,
            @Value("${spring.security.oauth2.client.registration.google.redirectUri}") String googleRedirectUri,
            @Value("${spring.security.oauth2.client.registration.facebook.clientId}") String facebookClientId,
            @Value("${spring.security.oauth2.client.registration.facebook.clientSecret}") String facebookClientSecret,
            @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}") String facebookRedirectUri,
            @Value("${spring.security.oauth2.client.registration.naver.clientId}") String naverClientId,
            @Value("${spring.security.oauth2.client.registration.naver.clientSecret}") String naverClientSecret
    ) {
        List<ClientRegistration> registrations = new ArrayList<>();

        registrations.add(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .redirectUri(googleRedirectUri)
                .build()
        );

        registrations.add(CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
                .clientId(facebookClientId)
                .clientSecret(facebookClientSecret)
                .redirectUri(facebookRedirectUri)
                .build()
        );

        registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .build()
        );

        return new InMemoryClientRegistrationRepository(registrations);
    }
}
