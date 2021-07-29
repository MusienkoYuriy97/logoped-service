package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.dto.request.FormRequest;
import by.logoped.logopedservice.dto.response.LogopedInfoResponse;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.service.UserService;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectCreator objectCreator;
    @MockBean
    private UserService userService;

    @Test
    void findLogopedById() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        when(userService.findLogopedById(any(Long.class))).thenReturn(new LogopedInfoResponse());
        //then
        this.mockMvc
                .perform(
                        get("/api/v1/user/find/"+ LOGOPED_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }


    @Test
    void findLogopedByIdNotFound() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        when(userService.findLogopedById(any(Long.class))).thenThrow(UserNotFoundException.class);
        //then
        this.mockMvc
                .perform(
                        get("/api/v1/user/find/"+ LOGOPED_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void findLogopedByIdNoToken() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/user/find/"+ LOGOPED_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    void findAllLogoped() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        when(userService.findAllLogoped()).thenReturn(new ArrayList<LogopedInfoResponse>());
        //then
        this.mockMvc
                .perform(
                        get("/api/v1/user/findall")
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void findAllLogopedNoToken() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/user/findall"))
                .andExpect(status().isForbidden());
    }

    @Test
    void sendFormToLogoped() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        doNothing().when(userService).addFormRequest(any(FormRequest.class));
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/user/form")
                                .header("Authorization", adminJwtToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.formRequest()))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void sendFormToLogopedLogopedNotFound() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        doThrow(UserNotFoundException.class).when(userService).addFormRequest(any(FormRequest.class));
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/user/form")
                                .header("Authorization", adminJwtToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectCreator.toJson(objectCreator.formRequest()))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void sendFormToLogopedNoParam() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        //then
        this.mockMvc
                .perform(
                        post("/api/v1/user/form")
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isBadRequest());
    }
}