package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.service.AdminService;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectCreator objectCreator;
    @MockBean
    private AdminService adminService;

    @Test
    void userBan() throws Exception {
        //when
        String adminJwtToken = objectCreator.adminJwtToken();
        doNothing().when(adminService).ban(any());
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/ban/2")
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isAccepted());
    }
}