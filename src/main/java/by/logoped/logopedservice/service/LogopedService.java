package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.LogopedInfoResponse;
import by.logoped.logopedservice.entity.ActivateKey;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.exception.ActiveKeyNotValidException;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.jwt.ActivateKeyJwtProvider;
import by.logoped.logopedservice.mapper.ObjectMapper;
import by.logoped.logopedservice.repository.ActivateKeyRepository;
import by.logoped.logopedservice.repository.LogopedRepository;
import by.logoped.logopedservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static by.logoped.logopedservice.util.UserStatus.ACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogopedService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.claimSimpleKey}")
    private String claimSimpleKey;
    @Value("${jwt.claimExpiration}")
    private String claimExpiration;

    private final UserRepository userRepository;
    private final ActivateKeyRepository activateKeyRepository;
    private final ActivateKeyJwtProvider activateKeyJwtProvider;
    private final LogopedRepository logopedRepository;
    private final ObjectMapper objectMapper;

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
        LocalDateTime expirationDate = LocalDateTime.parse(expiration.toString());
        return LocalDateTime.now().isAfter(expirationDate);
    }

    private ActivateKey getActivateKeyOrThrowException(String simpleKey) {
        return activateKeyRepository.getBySimpleKey(simpleKey)
                .orElseThrow(() -> new ActiveKeyNotValidException("Activate key doesn't exist"));
    }

    public LogopedInfoResponse getById(Long logopedId) {
        if (logopedId == null){
            log.error("logoped id shouldn't be null");
            throw new UserDataException("logoped id shouldn't be null");
        }
        Optional<Logoped> optionalLogoped = logopedRepository.findById(logopedId);
        if (optionalLogoped.isPresent()){
            return objectMapper.toLogopedResponse(optionalLogoped.get());
        }
        throw new UserDataException("Logoped not found");
    }
}
