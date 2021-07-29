package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.response.FormResponse;
import by.logoped.logopedservice.entity.ActivateKey;
import by.logoped.logopedservice.entity.Form;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.exception.ActiveKeyNotValidException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.jwt.ActivateKeyJwtProvider;
import by.logoped.logopedservice.repository.ActivateKeyRepository;
import by.logoped.logopedservice.repository.FormRepository;
import by.logoped.logopedservice.repository.LogopedRepository;
import by.logoped.logopedservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        User currentUser = UserDetailsServiceImpl.getCurrentUser();
        Optional<Logoped> optionalLogoped = logopedRepository.findByUser(currentUser);
        if (optionalLogoped.isPresent()){
            List<FormResponse> formResponses = new ArrayList<>();
            formRepository.findAllByLogoped(optionalLogoped.get()).forEach(form -> {
                FormResponse response = new FormResponse();
                response.setDescription(form.getDescription());
                response.setPhoneNumber(form.getPhoneNumber());
                formResponses.add(response);
            });
            return formResponses;
        }else {
            log.error("Logoped not found in database");
            throw new UserNotFoundException("Logoped not found in database");
        }
    }

    //TODO
    public FormResponse getForm(String userId) {
        log.info("Get form requests for by id");
        User currentUser = UserDetailsServiceImpl.getCurrentUser();
        Optional<Logoped> optionalLogoped = logopedRepository.findByUser(currentUser);
        if(optionalLogoped.isPresent()){
            Optional<Form> optionalForm = formRepository.findByLogoped(optionalLogoped.get());
            if (optionalForm.isPresent()){
                FormResponse response = new FormResponse();
                response.setDescription(optionalForm.get().getDescription());
                response.setPhoneNumber(optionalForm.get().getPhoneNumber());
                return FormResponse
                        .builder()
                            .description(optionalForm.get().getDescription())
                            .phoneNumber(optionalForm.get().getPhoneNumber())
                        .build();
            }else {
                throw new RuntimeException();
            }
        }else {
            log.error("Logoped not found in database");
            throw new UserNotFoundException("Logoped not found in database");
        }
    }
}
