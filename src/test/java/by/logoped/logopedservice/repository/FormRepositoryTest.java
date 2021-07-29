package by.logoped.logopedservice.repository;

import by.logoped.logopedservice.entity.Form;
import by.logoped.logopedservice.entity.Logoped;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static by.logoped.logopedservice.test.TestConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FormRepositoryTest {
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private LogopedRepository logopedRepository;

    @BeforeEach
    public void init(){
        Form form = new Form();
        form.setPhoneNumber("+375298344491");
        form.setDescription("Не выговаривает звук ль");
        form.setPhoneNumber("+375298344491");
        form.setLogoped(logopedRepository.getById(ADMIN_ID));
        formRepository.save(form);
    }

    @Test
    void findAllByLogoped() {
        final Logoped byId = logopedRepository.getById(ADMIN_ID);
        final List<Form> allByLogoped = formRepository.findAllByLogoped(byId);
        assertFalse(allByLogoped.isEmpty());
    }
}