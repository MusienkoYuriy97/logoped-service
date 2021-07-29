package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.test.TestConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        final Optional<User> optionalUser = userRepository.findByEmail(ADMIN_EMAIL);
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void existsByEmail() {
        assertTrue(userRepository.existsByEmail(ADMIN_EMAIL));
    }

    @Test
    void existsByPhoneNumber() {
        userRepository.existsByPhoneNumber(ADMIN_PHONE_NUMBER);
    }
}