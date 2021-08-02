package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.request.FormRequest;
import by.logoped.logopedservice.dto.response.GetFileResponse;
import by.logoped.logopedservice.dto.response.LessonResponse;
import by.logoped.logopedservice.dto.response.LogopedInfoResponse;
import by.logoped.logopedservice.entity.*;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.mapper.ObjectMapper;
import by.logoped.logopedservice.repository.FileRepository;
import by.logoped.logopedservice.repository.FormRepository;
import by.logoped.logopedservice.repository.LessonRepository;
import by.logoped.logopedservice.repository.LogopedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final LogopedRepository logopedRepository;
    private final FormRepository formRepository;
    private final FileRepository fileRepository;
    private final LessonRepository lessonRepository;
    private final ObjectMapper objectMapper;

    public LogopedInfoResponse findLogopedById(Long logopedId) {
        log.info("Get logoped by id");
        if (logopedId == null){
            log.error("logoped id shouldn't be null");
            throw new UserDataException("logoped id shouldn't be null");
        }
        Optional<Logoped> optionalLogoped = logopedRepository.findById(logopedId);
        if (optionalLogoped.isPresent()){
            return objectMapper.toLogopedResponse(optionalLogoped.get());
        }
        log.error("logoped not found");
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

    public List<GetFileResponse> getAllFiles() {
        log.info("Get all user files");
        User currentUser = UserDetailsServiceImpl.getCurrentUser();
        List<File> files = fileRepository.getAllByUser(currentUser);
        return toFileResponseList(files);
    }

    static List<GetFileResponse> toFileResponseList(List<File> files) {
        List<GetFileResponse> responseList = new ArrayList<>();
        files.forEach(file -> {
            Long userId;
            if (file.getUser() == null){
                userId = null;
            }else {
                userId = file.getUser().getId();
            }
            responseList.add(GetFileResponse
                    .builder()
                        .logopedId(file.getLogoped().getId())
                        .userId(userId)
                        .downloadLink(file.getDownloadLink())
                        .fileName(file.getFileName())
                    .build()
            );
        });
        return responseList;
    }

    public List<LessonResponse> getAllLesson() {
        log.info("Get all lessons for user");
        User user = UserDetailsServiceImpl.getCurrentUser();
        List<LessonResponse> responseList = new ArrayList<>();
        List<Lesson> allByUser = lessonRepository.getAllByUser(user);
        allByUser.forEach(lesson -> {
            responseList.add(LessonResponse
                    .builder()
                        .logopedId(lesson.getLogoped().getId())
                        .userId(lesson.getUser().getId())
                        .startTime(lesson.getStartTime())
                        .address(lesson.getAddress())
                        .zoomLink(lesson.getZoomlink())
                    .build());

        });
        return responseList;
    }
}