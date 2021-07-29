package by.logoped.logopedservice.service;

import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.repository.UserRepository;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static by.logoped.logopedservice.test.TestConstant.USER_EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceImplTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectCreator objectCreator;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername() {
        //mock
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(objectCreator.user()));
        User user = objectCreator.user();
        //call service
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_EMAIL);
        //assert
        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameThrowException() {
        //mock
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());
        //call service and assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(USER_EMAIL));
    }

    @Test
    void getCurrentEmail() {
        //mock
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(objectCreator.user()));
        objectCreator.buildSecurityContextForUser();
        //call service
        String currentEmail = UserDetailsServiceImpl.getCurrentEmail();
        //assert
        assertEquals(USER_EMAIL, currentEmail);
    }

    @Test
    void getCurrentUser() {
        //mock
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(objectCreator.user()));
        objectCreator.buildSecurityContextForUser();
        //call service
        User currentUser = UserDetailsServiceImpl.getCurrentUser();
        //assert
        assertEquals(currentUser.getEmail(), USER_EMAIL);
    }
}