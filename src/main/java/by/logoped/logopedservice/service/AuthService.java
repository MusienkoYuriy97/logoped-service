package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.LoginUserRequest;
import by.logoped.logopedservice.dto.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.RegistrationUserRequest;
import by.logoped.logopedservice.dto.RegistrationResponse;
import by.logoped.logopedservice.entity.*;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.jwt.JwtTokenProvider;
import by.logoped.logopedservice.mapper.ObjectMapper;
import by.logoped.logopedservice.repository.*;
import by.logoped.logopedservice.util.StringGenerator;
import by.logoped.logopedservice.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static by.logoped.logopedservice.util.UserStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivateKeyRepository activateKeyRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final LogopedRepository logopedRepository;
    private final CategoryRepository categoryRepository;

    @Value("${role.logoped}")
    private String roleLogoped;
    @Value("${role.user}")
    private String roleUser;

    @Transactional
    public RegistrationResponse saveUser(RegistrationUserRequest request) {
        log.info("Saving new user first name:{}; email:{}", request.getFirstName(), request.getEmail());
        return objectMapper.toUserResponse(save(request, roleUser, ACTIVE));
    }

    @Transactional
    public RegistrationResponse saveLogoped(RegistrationLogopedRequest request) {
        log.info("Saving new logoped first name:{}; email:{}", request.getFirstName(), request.getEmail());
        //save as user
        User user = save(request, roleLogoped, BLOCKED);
        //save as logoped
        Logoped logoped = objectMapper.toLogoped(request);
        logoped.setUser(user);
        logoped.setCategories(getCategorySetOrThrowException(request.getServices()));
        logopedRepository.save(logoped);

        //send email
        String simpleKey = saveActivateKey(user.getEmail());
        emailService.sendEmail(user.getEmail(), user.getFirstName(), simpleKey);
        return objectMapper.toUserResponse(user);
    }

    private Set<Category> getCategorySetOrThrowException(Set<String> requestServiceProvidedSet) {
        Set<Category> resultSet = new HashSet<>();
        for (String elm : requestServiceProvidedSet) {
            Optional<Category> optionalCategory =
                    categoryRepository.getByCategoryName(elm);
            if (optionalCategory.isPresent()){
                resultSet.add(optionalCategory.get());
            }else {
                throw new UserDataException("Service provided entered incorrectly");
            }
        }
        return resultSet;
    }

    private String saveActivateKey(String email){
        log.info("Saving activate key for {}", email);
        User user = getByEmailOrThrowException(email);
        ActivateKey activateKey = new ActivateKey();
        activateKey.setSimpleKey(StringGenerator.generate(12));
        activateKey.setUser(user);
        return activateKeyRepository.save(activateKey).getSimpleKey();
    }

    private User save(RegistrationUserRequest request, String roleLogoped, UserStatus blocked) {
        if (userRepository.existsByEmail(request.getEmail())){
            log.error("User with this email:{} already exist", request.getEmail());
            throw new UserDataException("User with this email already exist");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            log.error("User with this phoneNumber:{} already exist", request.getPhoneNumber());
            throw new UserDataException("User with this phone number already exist");
        }

        Role logopedRole = getRoleOrThrowException(roleLogoped);
        User user = objectMapper.toUser(request);
        user.setUserRoles(Set.of(logopedRole));
        user.setUserStatus(blocked);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.debug("Saved user {}", user);
        return user;
    }

    private Role getRoleOrThrowException(String roleName){
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role doesn't exist"));
    }

    public Map<String, String> login(LoginUserRequest loginUserRequest){
        log.info("Try to login in account {}", loginUserRequest.getEmail());
        User user;
        try {
            user = getByEmailOrThrowException(loginUserRequest.getEmail());
            log.debug("Get user by email:{}", user);
        }catch (UserDataException ex){
            log.warn("User with email:{} doesn't exist", loginUserRequest.getEmail());
            throw new UsernameNotFoundException("Wrong email/password");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getEmail(),
                loginUserRequest.getPassword()));
        Set<String> strings = new HashSet<>();
        user.getUserRoles().forEach(role -> strings.add(role.getRoleName()));
        return jwtTokenProvider.createToken(user.getId().toString(),
                        user.getEmail(),
                        strings);
    }

    private User getByEmailOrThrowException(String email){
        if (email == null){
            throw new UserDataException("Email is null.");
        }

        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserDataException("User with this email doesn't exist"));
    }
}
