package by.logoped.logopedservice.mapper;

import by.logoped.logopedservice.dto.request.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.request.RegistrationUserRequest;
import by.logoped.logopedservice.dto.response.LogopedInfoResponse;
import by.logoped.logopedservice.dto.response.RegistrationResponse;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ObjectMapperImplTest {
    @Autowired
    private ObjectCreator objectCreator;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void toUserResponse() {
        final User user = objectCreator.user();
        final RegistrationResponse response = objectMapper.toUserResponse(user);
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(user.getUserRoles().size(), response.getRoles().size());
    }

    @Test
    void toUser() {
        final RegistrationUserRequest request = objectCreator.userCreateRequest();
        final User user = objectMapper.toUser(request);
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(request.getPassword(), user.getPassword());
    }

    @Test
    void toLogoped() {
        final RegistrationLogopedRequest request = objectCreator.logopedCreateRequest();
        final Logoped logoped = objectMapper.toLogoped(request);
        assertEquals(request.getEducation(), logoped.getEducation());
        assertEquals(request.getWorkExperience(), logoped.getWorkExperience());
        assertEquals(request.getWorkPlace(), logoped.getWorkPlace());
    }

    @Test
    void toLogopedResponse() {
        final Logoped logoped = objectCreator.logoped();
        final LogopedInfoResponse response = objectMapper.toLogopedResponse(logoped);
        assertEquals(logoped.getEducation(), response.getEducation());
        assertEquals(logoped.getWorkExperience(), response.getWorkExperience());
        assertEquals(logoped.getWorkPlace(), response.getWorkPlace());
        assertEquals(logoped.getUser().getEmail(), response.getEmail());
        assertEquals(logoped.getUser().getFirstName(), response.getFirstName());
        assertEquals(logoped.getUser().getLastName(), response.getLastName());
        assertEquals(logoped.getUser().getPhoneNumber(), response.getPhoneNumber());
    }
}