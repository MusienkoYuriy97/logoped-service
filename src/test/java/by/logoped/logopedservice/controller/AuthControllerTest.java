package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.request.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.request.RegistrationUserRequest;
import by.logoped.logopedservice.exception.NoActivatedAccountException;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.service.AuthService;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectCreator objectCreator;
    @MockBean
    private AuthService authService;

    @Test
    void registrationUser() throws Exception {
        //when
        when(authService.saveUser(any(RegistrationUserRequest.class))).thenReturn(objectCreator.registerUserResponse());
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/registration/user")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.userCreateRequest()))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.phoneNumber").value(USER_PHONE_NUMBER));
    }

    @Test
    void registrationUserEmailAlreadyExist() throws Exception {
        //when
        when(authService.saveUser(any(RegistrationUserRequest.class))).thenThrow(UserDataException.class);
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/registration/user")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.userCreateRequest()))
                )
                .andExpect(status().isConflict());
    }

    @Test
    void registrationUserWithoutParam() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/registration/user"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void registrationLogoped() throws Exception {
        //when
        when(authService.saveLogoped(any(RegistrationLogopedRequest.class))).thenReturn(objectCreator.registerLogopedResponse());
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/registration/logoped")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.logopedCreateRequest()))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(LOGOPED_EMAIL))
                .andExpect(jsonPath("$.phoneNumber").value(LOGOPED_PHONE_NUMBER));
    }

    @Test
    void registrationLogopedEmailAlreadyExist() throws Exception {
        //when
        when(authService.saveLogoped(any(RegistrationLogopedRequest.class))).thenThrow(UserDataException.class);
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/registration/logoped")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.logopedCreateRequest()))
                )
                .andExpect(status().isConflict());
    }

    @Test
    void registrationLogopedWithoutParam() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/registration/logoped"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login() throws Exception {
        //when
        when(authService.login(objectCreator.loginUserRequest())).thenReturn(new HashMap<>());
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/login")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.loginUserRequest()))
                )
                .andExpect(status().isOk());
    }

    @Test
    void loginWrongPasswordOrEmail() throws Exception {
        //when
        when(authService.login(objectCreator.loginUserRequest())).thenThrow(UsernameNotFoundException.class);
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/login")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.loginUserRequest()))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void loginWrongBlockedAccount() throws Exception {
        //when
        when(authService.login(objectCreator.loginUserRequest())).thenThrow(NoActivatedAccountException.class);
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/auth/login")
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.loginUserRequest()))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void loginWithoutParam() throws Exception {
        this.mockMvc
                .perform(post("/api/v1/auth/login"))
                .andExpect(status().isBadRequest());
    }
}