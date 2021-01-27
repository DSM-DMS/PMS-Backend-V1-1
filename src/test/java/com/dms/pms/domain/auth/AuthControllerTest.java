package com.dms.pms.domain.auth;

import com.dms.pms.domain.AbstractControllerTest;
import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.Parent;
import com.dms.pms.entity.pms.user.ParentRepository;
import com.dms.pms.payload.request.LoginRequest;
import com.dms.pms.security.auth.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        parentRepository.save(
                Parent.builder()
                .email("user@gmail.com")
                .password(passwordEncoder.encode("11111111"))
                .name("user")
                .roleType(RoleType.USER)
                .authProvider(AuthProvider.local)
                .build()
        );

        parentRepository.save(
                Parent.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("11111111"))
                .name("admin")
                .authProvider(AuthProvider.local)
                .roleType(RoleType.ADMIN)
                .build()
        );
    }

    @AfterEach
    public void after() {
        parentRepository.deleteAll();
    }

    @Test
    @DisplayName("Login test")
    public void login() throws Exception {
        LoginRequest request = new LoginRequest("user@gmail.com", "11111111");

        mockMvc.perform(
                post("/auth")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Login test with bad request")
    public void loginWithBadRequest() throws Exception {
        LoginRequest request = new LoginRequest();

        mockMvc.perform(
                post("/auth")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Login test with Unauthorized")
    public void loginWithUnauthorized() throws Exception {
        LoginRequest request = new LoginRequest("admin@gmail.com", "1111");

        mockMvc.perform(
                post("/auth")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
