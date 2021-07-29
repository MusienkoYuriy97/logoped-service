package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.service.AdminService;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.mockito.Mockito.*;
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
        doNothing().when(adminService).ban(USER_ID);
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/ban/" + USER_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isAccepted());
    }

    @Test
    void userBanUserNotFound() throws Exception {
        //when
        String adminJwtToken = objectCreator.adminJwtToken();
        doThrow(UserNotFoundException.class).when(adminService).ban(USER_ID);
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/ban/" + USER_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void userBanForYourself() throws Exception {
        //when
        String adminJwtToken = objectCreator.adminJwtToken();
        doThrow(UserDataException.class).when(adminService).ban(ADMIN_ID);
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/ban/" + ADMIN_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void userBanNoToken() throws Exception {
        this.mockMvc
                .perform(patch("/api/v1/admin/ban/" + USER_ID))
                .andExpect(status().isForbidden());
    }


    @Test
    void userUnBan() throws Exception {
        //when
        String adminJwtToken = objectCreator.adminJwtToken();
        doNothing().when(adminService).unBan(USER_ID);
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/unban/" + USER_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isAccepted());
    }

    @Test
    void userUnBanUserNotFound() throws Exception {
        //when
        String adminJwtToken = objectCreator.adminJwtToken();
        doThrow(UserNotFoundException.class).when(adminService).unBan(USER_ID);
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/unban/" + USER_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void userUnBanForYourself() throws Exception {
        //when
        String adminJwtToken = objectCreator.adminJwtToken();
        doThrow(UserDataException.class).when(adminService).unBan(ADMIN_ID);
        //then
        this.mockMvc
                .perform(
                        patch("/api/v1/admin/unban/" + ADMIN_ID)
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void userUnBanNoToken() throws Exception {
        this.mockMvc
                .perform(patch("/api/v1/admin/unban/" + USER_ID))
                .andExpect(status().isForbidden());
    }
}