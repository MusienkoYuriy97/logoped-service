package by.logoped.logopedservice.controller;

import by.logoped.logopedservice.exception.ActiveKeyNotValidException;
import by.logoped.logopedservice.service.LogopedService;
import by.logoped.logopedservice.test.ObjectCreator;
import by.logoped.logopedservice.test.TestConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LogopedControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectCreator objectCreator;
    @MockBean
    private LogopedService logopedService;

    @Test
    void activate() throws Exception {
        //when
        doNothing().when(logopedService).activate(any(String.class));
        //then
        this.mockMvc
                .perform(get("/api/v1/logoped/activate/" + ACTIVATE_KEY))
                .andExpect(status().isAccepted());
    }

    @Test
    void activateKeyNotValid() throws Exception {
        doThrow(ActiveKeyNotValidException.class).when(logopedService).activate(any(String.class));

        this.mockMvc
                .perform(get("/api/v1/logoped/activate/" + ACTIVATE_KEY))
                .andExpect(status().isConflict());
    }

    @Test
    void getAllForm() throws Exception {
        //when
        final String adminJwtToken = objectCreator.adminJwtToken();
        when(logopedService.getAllForm()).thenReturn(new ArrayList<>());
        //then
        this.mockMvc
                .perform(
                        get("/api/v1/logoped/form/getall")
                                .header("Authorization", adminJwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void getAllFormNoToken() throws Exception {

        this.mockMvc
                .perform(
                        get("/api/v1/logoped/form/getall")
                )
                .andExpect(status().isForbidden());
    }
}