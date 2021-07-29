package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.request.LoginUserRequest;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.repository.UserRepository;
import by.logoped.logopedservice.test.ObjectCreator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.Optional;

import static by.logoped.logopedservice.test.TestConstant.USER_EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ObjectCreator objectCreator;

    @Test
    void saveUser() {
    }

    @Test
    void saveLogoped() {
    }


    @Test
    void login() {
        //mock
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(objectCreator.user()));
        objectCreator.buildSecurityContextForUser();
        //call method
        Map<String, String> tokenMap = authService.login(objectCreator.loginUserRequest());
        //assert
        assertTrue(tokenMap.containsKey("access_token"));
        assertNotNull(tokenMap.get("access_token"));
    }

    @Test
    void loginUserNotExist() {
        //mock
        LoginUserRequest loginUserRequest = objectCreator.loginUserRequest();
        loginUserRequest.setEmail("dfgdfg");
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(objectCreator.user()));
        //call method and assert throw
        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginUserRequest));
    }
}