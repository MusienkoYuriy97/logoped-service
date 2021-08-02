package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.File;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> getAllByLogoped(Logoped logoped);
    List<File> getAllByUser(User user);
}
