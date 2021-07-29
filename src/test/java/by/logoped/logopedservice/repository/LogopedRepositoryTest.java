package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static by.logoped.logopedservice.test.TestConstant.ADMIN_ID;
import static by.logoped.logopedservice.test.TestConstant.USER_ID;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LogopedRepositoryTest {
    @Autowired
    private LogopedRepository logopedRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUser() {
        final User user = userRepository.getById(ADMIN_ID);
        final Optional<Logoped> optionalLogoped = logopedRepository.findByUser(user);
        assertTrue(optionalLogoped.isPresent());
    }

    @Test
    void findByUserNotFound() {
        final User user = userRepository.getById(USER_ID);
        final Optional<Logoped> optionalLogoped = logopedRepository.findByUser(user);
        assertTrue(optionalLogoped.isEmpty());
    }
}