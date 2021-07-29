package by.logoped.logopedservice.service;

import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.repository.UserRepository;
import by.logoped.logopedservice.test.ObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectCreator objectCreator;
    @Autowired
    private AdminService adminService;

    @Test
    void ban() {
        //mock
        when(userRepository.findByEmail(ADMIN_EMAIL)).thenReturn(Optional.of(objectCreator.admin()));
        objectCreator.buildSecurityContextForAdmin();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(objectCreator.user()));
        when(userRepository.save(any(User.class))).thenReturn(objectCreator.bannedUser());
        //call service
        adminService.ban(USER_ID);
        //verify
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void unBan() {
        //mock
        when(userRepository.findByEmail(ADMIN_EMAIL)).thenReturn(Optional.of(objectCreator.admin()));
        objectCreator.buildSecurityContextForAdmin();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(objectCreator.user()));
        when(userRepository.save(any(User.class))).thenReturn(objectCreator.bannedUser());
        //call service
        adminService.ban(USER_ID);
        //verify
        verify(userRepository, times(1)).save(any(User.class));
    }
}