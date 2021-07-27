package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.ActivateKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivateKeyRepository extends JpaRepository<ActivateKey, Long> {
    long deleteActivateKeyBySimpleKey(String simpleKey);
    Optional<ActivateKey> getBySimpleKey(String simpleKey);
}
