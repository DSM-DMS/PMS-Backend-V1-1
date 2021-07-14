package com.dms.pms.domain.user;

import com.dms.pms.domain.AbstractControllerTest;
import com.dms.pms.entity.pms.student.Student;
import com.dms.pms.entity.pms.student.StudentRepository;
import com.dms.pms.entity.pms.user.AuthProvider;
import com.dms.pms.entity.pms.user.User;
import com.dms.pms.entity.pms.user.UserRepository;
import com.dms.pms.payload.request.RegisterRequest;
import com.dms.pms.payload.request.StudentAdditionRequest;
import com.dms.pms.security.JwtTokenProvider;
import com.dms.pms.security.auth.RoleType;
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
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String token;

    @BeforeEach
    public void setup() {
        userRepository.save(
                User.builder()
                .email("conflict@gmail.com")
                .password("11111111")
                .roleType(RoleType.USER)
                .authProvider(AuthProvider.LOCAL)
                .name("conflict")
                .build()
        );

        studentRepository.save(new Student(111111, "studentId", 1111));

        token = jwtTokenProvider.generateAccessToken("conflict@gmail.com", RoleType.USER);
    }

    @AfterEach
    public void after() {
        userRepository.deleteAll();
        studentRepository.deleteAll();
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

    // Student Test Code

    @Test
    @DisplayName("Student Addition Test")
    public void addStudent() throws Exception {
        StudentAdditionRequest request = new StudentAdditionRequest(111111);

        mockMvc.perform(post("/user/student")
            .header("abc", "Awsdsa " + token)
            .content(new ObjectMapper().writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @DisplayName("Student Addition Test with Bad Request")
    public void addStudentWithBadRequest() throws Exception {
        StudentAdditionRequest request = new StudentAdditionRequest();

        mockMvc.perform(post("/user/student")
                .header("abc", "Awsdsa " + token)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Student Addition Test with Unauthorized")
    public void addStudentWithUnauthorized() throws Exception {
        StudentAdditionRequest request = new StudentAdditionRequest(111111);

        mockMvc.perform(post("/user/student")
            .content(new ObjectMapper().writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @Test
    @DisplayName("Student Addition Test with Not found")
    public void addStudentWithNotFound() throws Exception {
        StudentAdditionRequest request = new StudentAdditionRequest(123456);

        mockMvc.perform(post("/user/student")
                .header("abc", "Awsdsa " + token)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
