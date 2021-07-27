package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getByCategoryName(String categoryName);
}
