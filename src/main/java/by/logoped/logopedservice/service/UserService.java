package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.request.FormRequest;
import by.logoped.logopedservice.dto.response.LogopedInfoResponse;
import by.logoped.logopedservice.entity.Form;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.mapper.ObjectMapper;
import by.logoped.logopedservice.repository.FormRepository;
import by.logoped.logopedservice.repository.LogopedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final LogopedRepository logopedRepository;
    private final FormRepository formRepository;
    private final ObjectMapper objectMapper;

    public LogopedInfoResponse findLogopedById(Long logopedId) {
        if (logopedId == null){
            log.error("logoped id shouldn't be null");
            throw new UserDataException("logoped id shouldn't be null");
        }
        Optional<Logoped> optionalLogoped = logopedRepository.findById(logopedId);
        if (optionalLogoped.isPresent()){
            return objectMapper.toLogopedResponse(optionalLogoped.get());
        }
        throw new UserNotFoundException("Logoped not found");
    }

    public List<LogopedInfoResponse> findAllLogoped() {
        log.info("Get all logoped for user");
        final List<Logoped> logopedList = logopedRepository.findAll();
        if (logopedList.isEmpty()){
            log.error("Any logoped not found in database");
            throw new UserNotFoundException("Any logoped not found");
        }
        return logopedList.stream()
                .map(objectMapper::toLogopedResponse)
                .collect(Collectors.toList());
    }

    public void addFormRequest(FormRequest request){
        log.info("Sending form request for logoped  id:{}", request.getLogopedId());
        final Optional<Logoped> optionalLogoped = logopedRepository.findById(request.getLogopedId());
        if (optionalLogoped.isPresent()) {
            Logoped logoped = optionalLogoped.get();
            Form form = new Form();
            form.setLogoped(logoped);
            form.setDescription(request.getDescription());
            form.setPhoneNumber(request.getPhoneNumber());
            formRepository.save(form);
        }else {
            log.error("Logoped not found in database id:{}",request.getLogopedId());
            throw new UserNotFoundException("Logoped not found in database");
        }
    }
}
