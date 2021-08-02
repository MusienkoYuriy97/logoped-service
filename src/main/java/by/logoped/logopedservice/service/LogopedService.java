package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.request.LessonCreateRequest;
import by.logoped.logopedservice.dto.response.FormResponse;
import by.logoped.logopedservice.dto.response.GetFileResponse;
import by.logoped.logopedservice.dto.response.LessonResponse;
import by.logoped.logopedservice.dto.response.UploadResponse;
import by.logoped.logopedservice.entity.*;
import by.logoped.logopedservice.exception.ActiveKeyNotValidException;
import by.logoped.logopedservice.exception.FormNotFoundException;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.jwt.ActivateKeyJwtProvider;
import by.logoped.logopedservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static by.logoped.logopedservice.util.UserStatus.ACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogopedService {
    @Value("${jwt.claimSimpleKey}")
    private String claimSimpleKey;
    @Value("${jwt.claimExpiration}")
    private String claimExpiration;

    private final UserRepository userRepository;
    private final ActivateKeyRepository activateKeyRepository;
    private final ActivateKeyJwtProvider activateKeyJwtProvider;
    private final LogopedRepository logopedRepository;
    private final FormRepository formRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public void activate(String jwtActivateKey){
        log.info("Acivate account by activation link");
        if(jwtActivateKey == null){
            log.warn("Activate key cannot be null.");
            throw new ActiveKeyNotValidException("Activate key cannot be null.");
        }
        Map<String, Object> claimsMap = activateKeyJwtProvider.getClaimsMap(jwtActivateKey);
        String simpleKey = claimsMap.get(claimSimpleKey).toString();
        Object expiration = claimsMap.get(claimExpiration);
        if (isExpired(expiration)){
            activateKeyRepository.deleteActivateKeyBySimpleKey(simpleKey);
            log.warn("Activate key is expired");
            throw new ActiveKeyNotValidException("Activate key is expired");
        } else {
            ActivateKey activateKey = getActivateKeyOrThrowException(simpleKey);
            User user = activateKey.getUser();
            user.setUserStatus(ACTIVE);
            userRepository.save(user);
            activateKeyRepository.deleteActivateKeyBySimpleKey(simpleKey);
        }
    }

    private boolean isExpired(Object expiration) {
        log.info("Check if activate key is expired");
        LocalDateTime expirationDate = LocalDateTime.parse(expiration.toString());
        return LocalDateTime.now().isAfter(expirationDate);
    }

    private ActivateKey getActivateKeyOrThrowException(String simpleKey) {
        log.info("Get activate key from database");
        return activateKeyRepository.getBySimpleKey(simpleKey)
                .orElseThrow(() -> new ActiveKeyNotValidException("Activate key doesn't exist"));
    }

    public List<FormResponse> getAllForm() {
        log.info("Get all form requests for logoped");
        Logoped logoped = getCurrentLogopedOrThrowException();

        List<FormResponse> formResponses = new ArrayList<>();
        formRepository.findAllByLogoped(logoped).forEach(form -> {
            FormResponse response = new FormResponse();
            response.setDescription(form.getDescription());
            response.setPhoneNumber(form.getPhoneNumber());
            formResponses.add(response);
        });
        return formResponses;
    }

    public FormResponse getForm(Long formId) {
        log.info("Get form requests by id");
        Logoped logoped = getCurrentLogopedOrThrowException();
        Optional<Form> optionalForm = formRepository.findById(formId);
        if (optionalForm.isPresent() && optionalForm.get().getLogoped().getId() == logoped.getId()){
            return FormResponse
                    .builder()
                    .description(optionalForm.get().getDescription())
                    .phoneNumber(optionalForm.get().getPhoneNumber())
                    .build();
        }else {
            log.error("Form not found");
            throw new FormNotFoundException("Form not found");
        }
    }

    public void deleteForm(Long formId) {
        log.info("Delete request form by form id");
        Logoped logoped = getCurrentLogopedOrThrowException();
        Optional<Form> optionalForm = formRepository.findById(formId);
        if (optionalForm.isPresent() && optionalForm.get().getLogoped().getId() == logoped.getId()){
            formRepository.deleteById(formId);
        }else {
            log.error("Form not found");
            throw new FormNotFoundException("Form not found");
        }
    }

    public void createLesson(LessonCreateRequest request) {
        log.info("Create lesson request {}", request);
        Logoped currentLogoped = getCurrentLogopedOrThrowException();
        lessonRepository.save(
                Lesson
                        .builder()
                            .logoped(currentLogoped)
                            .zoomlink(request.getZoomLink())
                            .startTime(request.getStartTime())
                            .user(getUserOrThrowException(request.getUserId()))
                        .build()
        );
    }

    public List<LessonResponse> getAllLesson() {
        log.info("Get all logoped lesson");
        Logoped currentlogoped = getCurrentLogopedOrThrowException();
        List<LessonResponse> responseList = new ArrayList<>();
        List<Lesson> allByLogoped = lessonRepository.getAllByLogoped(currentlogoped);
        allByLogoped.forEach(lesson -> {
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

    public void uploadFile(MultipartFile multipartFile){
        log.info("Upload file for logoped");
        Logoped currentLogoped = getCurrentLogopedOrThrowException();
        UploadResponse upload = fileService.upload(multipartFile);
        File file = new File();
        file.setFileName(upload.getFileName());
        file.setDownloadLink(upload.getDownloadLink());
        file.setLogoped(currentLogoped);
        fileRepository.save(file);
    }

    public void uploadFile(MultipartFile multipartFile, Long userId){
        log.info("Upload file for user by logoped");
        Logoped currentLogoped = getCurrentLogopedOrThrowException();
        User user = getUserOrThrowException(userId);
        UploadResponse upload = fileService.upload(multipartFile);
        File file = new File();
        file.setFileName(upload.getFileName());
        file.setDownloadLink(upload.getDownloadLink());
        file.setLogoped(currentLogoped);
        file.setUser(user);
        fileRepository.save(file);
    }

    public List<GetFileResponse> getAllFiles() {
        log.info("Get all files");
        Logoped currentLogoped = getCurrentLogopedOrThrowException();
        List<File> files = fileRepository.getAllByLogoped(currentLogoped);
        return UserService.toFileResponseList(files);
    }

    private Logoped getCurrentLogopedOrThrowException(){
        log.info("Get logoped from security context");
        User currentUser = UserDetailsServiceImpl.getCurrentUser();
        Optional<Logoped> optionalLogoped = logopedRepository.findByUser(currentUser);
        if (optionalLogoped.isPresent()){
            return optionalLogoped.get();
        }
        log.error("Logoped not found in database");
        throw new UserNotFoundException("Logoped not found in databse");
    }

    private User getUserOrThrowException(Long userId){
        log.info("Get user from databse user id {}", userId);
        if (userId == null){
            log.error("user id cannot be null");
            throw new UserDataException("user id cannot be null");
        }
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        log.error("User not found in database");
        throw new UserNotFoundException("User not found in databse");
    }
}