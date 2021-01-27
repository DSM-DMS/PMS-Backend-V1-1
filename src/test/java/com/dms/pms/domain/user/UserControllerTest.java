package com.dms.pms.domain.user;

import com.dms.pms.domain.AbstractControllerTest;
import com.dms.pms.entity.pms.user.Parent;
import com.dms.pms.entity.pms.user.ParentRepository;
import com.dms.pms.payload.request.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private ParentRepository parentRepository;

    @BeforeEach
    public void setup() {
        parentRepository.save(
                Parent.builder()
                .email("conflict@gmail.com")
                .password("11111111")
                .name("conflict")
                .build()
        );
    }

    @AfterEach
    public void after() {
        parentRepository.deleteAll();
    }

    @Test
    @DisplayName("Register Test")
    public void register() throws Exception {
        RegisterRequest request = new RegisterRequest("smoothbear@gmail.com", "11111111", "smoothbear");

        mockMvc.perform(
                post("/user")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Register Test With Bad Request")
    public void registerWithBadRequest() throws Exception {
        RegisterRequest request = new RegisterRequest();

        mockMvc.perform(
                post("/user")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Register Test With Conflict")
    public void registerWithConflict() throws Exception {
        RegisterRequest request = new RegisterRequest("conflict@gmail.com", "11111111", "conflict");

        mockMvc.perform(
                post("/user")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }
}
