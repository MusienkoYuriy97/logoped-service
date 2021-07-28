package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Form;
import by.logoped.logopedservice.entity.Logoped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByLogoped(Logoped logoped);
}
