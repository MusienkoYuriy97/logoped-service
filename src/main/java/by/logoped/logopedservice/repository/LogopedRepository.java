package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogopedRepository extends JpaRepository<Logoped, Long> {
    Optional<Logoped> findByUser(User user);
}
