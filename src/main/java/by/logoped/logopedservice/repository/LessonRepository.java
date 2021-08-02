package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Lesson;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> getAllByLogoped(Logoped logoped);
    List<Lesson> getAllByUser(User user);
}
