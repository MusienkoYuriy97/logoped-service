package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init(){
        Category category = new Category();
        category.setCategoryName("Логопед");
        categoryRepository.save(category);
    }

    @Test
    void getByCategoryName() {
        final Optional<Category> optionalCategory = categoryRepository.getByCategoryName("Логопед");
        assertTrue(optionalCategory.isPresent());
    }

    @Test
    void getByCategoryNameNotFound() {
        final Optional<Category> optionalCategory = categoryRepository.getByCategoryName("Логопед2");
        assertTrue(optionalCategory.isEmpty());
    }
}