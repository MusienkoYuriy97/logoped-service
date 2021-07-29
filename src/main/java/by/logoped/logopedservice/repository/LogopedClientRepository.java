package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.LogopedClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogopedClientRepository extends JpaRepository<LogopedClient, Long> {
}
