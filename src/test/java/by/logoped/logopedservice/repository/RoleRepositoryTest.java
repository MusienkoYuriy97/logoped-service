package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByRoleName() {
        final Optional<Role> optionalRole = roleRepository.findByRoleName("ROLE_USER");
        assertTrue(optionalRole.isPresent());
    }

    @Test
    void findByRoleNameNotFound() {
        final Optional<Role> optionalRole = roleRepository.findByRoleName("");
        assertTrue(optionalRole.isEmpty());
    }
}