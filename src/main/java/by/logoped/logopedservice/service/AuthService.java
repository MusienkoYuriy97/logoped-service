package by.logoped.logopedservice.service;

import by.logoped.logopedservice.dto.LoginUserRequest;
import by.logoped.logopedservice.dto.RegistrationRequest;
import by.logoped.logopedservice.dto.RegistrationResponse;
import by.logoped.logopedservice.entity.ActivateKey;
import by.logoped.logopedservice.entity.Role;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.jwt.JwtTokenProvider;
import by.logoped.logopedservice.mapper.UserMapper;
import by.logoped.logopedservice.repository.ActivateKeyRepository;
import by.logoped.logopedservice.repository.RoleRepository;
import by.logoped.logopedservice.repository.UserRepository;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;

    @Value("${role.logoped}")
    private String roleLogoped;
    @Value("${role.user}")
    private String roleUser;

    @Transactional
    public RegistrationResponse saveUser(RegistrationRequest request) {
        log.info("Saving new user first name:{}; email:{}", request.getFirstName(), request.getEmail());
        return save(request, roleUser, ACTIVE);
    }

    @Transactional
    public RegistrationResponse saveLogoped(RegistrationRequest request) {
        log.info("Saving new logoped first name:{}; email:{}", request.getFirstName(), request.getEmail());
        RegistrationResponse response = save(request, roleLogoped, BLOCKED);
        String simpleKey = saveActivateKey(response.getEmail());
        emailService.sendEmail(response.getEmail(), response.getFirstName(), simpleKey);
        return response;
    }

    private String saveActivateKey(String email){
        log.info("Saving activate key for {}", email);
        User user = getByEmailOrThrowException(email);
        ActivateKey activateKey = new ActivateKey();
        activateKey.setSimpleKey(StringGenerator.generate(12));
        activateKey.setUser(user);
        return activateKeyRepository.save(activateKey).getSimpleKey();
    }

    private RegistrationResponse save(RegistrationRequest request, String roleLogoped, UserStatus blocked) {
        if (userRepository.existsByEmail(request.getEmail())){
            log.error("User with this email:{} already exist", request.getEmail());
            throw new UserDataException("User with this email already exist");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            log.error("User with this phoneNumber:{} already exist", request.getPhoneNumber());
            throw new UserDataException("User with this phone number already exist");
        }

        Role logopedRole = getRoleOrThrowException(roleLogoped);
        User user = userMapper.toUser(request);
        user.setUserRoles(Set.of(logopedRole));
        user.setUserStatus(blocked);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.debug("Saved user {}", user);
        return userMapper.toDto(user);
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
