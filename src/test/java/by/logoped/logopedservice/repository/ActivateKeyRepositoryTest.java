package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.configuration.PasswordEncoderConfiguration;
import by.logoped.logopedservice.entity.ActivateKey;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static by.logoped.logopedservice.test.TestConstant.*;
import static by.logoped.logopedservice.util.UserStatus.ACTIVE;
import static by.logoped.logopedservice.util.UserStatus.BLOCKED;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PasswordEncoderConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActivateKeyRepositoryTest {
    @Autowired
    private ActivateKeyRepository activateKeyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init(){
        ActivateKey activateKey = new ActivateKey();
        User save = userRepository.save(user());
        activateKey.setUser(save);
        activateKey.setSimpleKey(SIMPLE_ACTIVATE_KEY);
        activateKeyRepository.save(activateKey);
    }

    @Test
    void deleteActivateKeyBySimpleKey() {
        long count = activateKeyRepository.deleteActivateKeyBySimpleKey(SIMPLE_ACTIVATE_KEY);
        assertEquals(1, count);
    }

    @Test
    void getBySimpleKey() {
        Optional<ActivateKey> bySimpleKey = activateKeyRepository.getBySimpleKey(SIMPLE_ACTIVATE_KEY);
        assertFalse(bySimpleKey.isEmpty());
    }

    private User user(){
        return User
                .builder()
                    .firstName(FIRST_LOGOPED_NAME)
                    .lastName(LAST_LOGOPED_NAME)
                    .email(LOGOPED_EMAIL)
                    .phoneNumber(LOGOPED_PHONE_NUMBER)
                    .password(passwordEncoder.encode(LOGOPED_PASSWORD))
                    .userStatus(BLOCKED)
                .build();
    }
}