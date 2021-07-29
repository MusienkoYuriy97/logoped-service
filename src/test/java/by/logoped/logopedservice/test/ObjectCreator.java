package by.logoped.logopedservice.test;

import by.logoped.logopedservice.dto.request.FormRequest;
import by.logoped.logopedservice.dto.request.LoginUserRequest;
import by.logoped.logopedservice.dto.request.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.request.RegistrationUserRequest;
import by.logoped.logopedservice.dto.response.RegistrationResponse;
import by.logoped.logopedservice.entity.Category;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.Role;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.jwt.JwtTokenProvider;
import by.logoped.logopedservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static by.logoped.logopedservice.test.TestConstant.*;
import static by.logoped.logopedservice.util.UserStatus.ACTIVE;
import static by.logoped.logopedservice.util.UserStatus.BANNED;

@Component
@RequiredArgsConstructor
public class ObjectCreator {
    @Value("${role.user}")
    private String userRole;
    @Value("${role.logoped}")
    private String logopedRole;
    @Value("${role.admin}")
    private String adminRole;
    @Value("${jwt.prefix}")
    private String prefix;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public User user(){
        Collection<Role> roles = new ArrayList<>();
        roles.add(new Role(1l, userRole));
        return User
                .builder()
                    .id(USER_ID)
                    .firstName(FIRST_USER_NAME)
                    .lastName(LAST_USER_NAME)
                    .email(USER_EMAIL)
                    .phoneNumber(USER_PHONE_NUMBER)
                    .password(passwordEncoder.encode(USER_PASSWORD))
                    .userRoles(roles)
                    .userStatus(ACTIVE)
                .build();
    }

    public User bannedUser(){
        User user = user();
        user.setUserStatus(BANNED);
        return user;
    }

    public User admin(){
        Collection<Role> roles = new ArrayList<>();
        roles.add(new Role(1l, adminRole));
        return User
                .builder()
                    .id(ADMIN_ID)
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .userStatus(ACTIVE)
                    .userRoles(roles)
                .build();
    }

    public Logoped logoped(){
        Collection<Role> roles = new ArrayList<>();
        roles.add(new Role(2l, logopedRole));
        final User user = User
                .builder()
                    .id(LOGOPED_ID)
                    .firstName(FIRST_LOGOPED_NAME)
                    .lastName(LAST_LOGOPED_NAME)
                    .email(LOGOPED_EMAIL)
                    .phoneNumber(LOGOPED_PHONE_NUMBER)
                    .password(passwordEncoder.encode(LOGOPED_PASSWORD))
                    .userRoles(roles)
                    .userStatus(ACTIVE)
                .build();
        return Logoped
                .builder()
                    .user(user)
                    .workPlace(LOGOPED_WORK_PLACE)
                    .workExperience(LOGOPED_WORK_EXPERIENCE)
                    .education(LOGOPED_EDUCATION)
                    .categories(Set.of(new Category(1l,"Подготовка к школе")))
                .build();
    }

    public void buildSecurityContextForUser(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(USER_EMAIL, USER_PASSWORD);
        Authentication authenticate = authenticationManager.authenticate(authReq);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);
    }

    public void buildSecurityContextForAdmin(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(ADMIN_EMAIL, ADMIN_PASSWORD);
        Authentication authenticate = authenticationManager.authenticate(authReq);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);
    }

    public String adminJwtToken(){
        Optional<User> admin = userRepository.findByEmail(ADMIN_EMAIL);
        Set<String> roles = new HashSet<>();
        admin.get().getUserRoles().forEach(role -> roles.add(role.getRoleName()));
        return prefix + admin.map(
                        u -> jwtTokenProvider.createToken(u.getId().toString(), u.getEmail(), roles)
                                .get("access_token")
                )
                .orElse(null);
    }

    public String userJwtToken(){
        Optional<User> user = userRepository.findByEmail(USER_EMAIL);
        Set<String> roles = new HashSet<>();
        user.get().getUserRoles().forEach(role -> roles.add(role.getRoleName()));
        return prefix + user.map(
                        u -> jwtTokenProvider.createToken(u.getId().toString(), u.getEmail(), roles)
                                .get("access_token")
                )
                .orElse(null);
    }

    public String toJson(Object o) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    public RegistrationResponse registerUserResponse() {
        return RegistrationResponse
                .builder()
                    .email(USER_EMAIL)
                    .firstName(FIRST_USER_NAME)
                    .lastName(LAST_USER_NAME)
                    .phoneNumber(USER_PHONE_NUMBER)
                    .roles(List.of(userRole))
                .build();
    }

    public RegistrationResponse registerLogopedResponse() {
        return RegistrationResponse
                .builder()
                    .email(LOGOPED_EMAIL)
                    .firstName(FIRST_LOGOPED_NAME)
                    .lastName(LAST_LOGOPED_NAME)
                    .phoneNumber(LOGOPED_PHONE_NUMBER)
                    .roles(List.of(logopedRole))
                .build();
    }

    public RegistrationUserRequest userCreateRequest() {
        return RegistrationUserRequest
                .builder()
                    .firstName(FIRST_USER_NAME)
                    .lastName(LAST_USER_NAME)
                    .email(USER_EMAIL)
                    .phoneNumber(USER_PHONE_NUMBER)
                    .password(USER_PASSWORD)
                .build();
    }

    public RegistrationLogopedRequest logopedCreateRequest() {
        RegistrationLogopedRequest request = new RegistrationLogopedRequest();
        request.setFirstName(FIRST_LOGOPED_NAME);
        request.setLastName(LAST_LOGOPED_NAME);
        request.setEmail(LOGOPED_EMAIL);
        request.setPhoneNumber(LOGOPED_PHONE_NUMBER);
        request.setPassword(LOGOPED_PASSWORD);
        request.setEducation(LOGOPED_EDUCATION);
        request.setWorkPlace(LOGOPED_WORK_PLACE);
        request.setWorkExperience(LOGOPED_WORK_EXPERIENCE);
        request.setCategories(Set.of("Подготовка к школе", "Логопед"));
        return request;
    }

    public LoginUserRequest loginUserRequest() {
        return LoginUserRequest
                .builder()
                    .email(USER_EMAIL)
                    .password(USER_PASSWORD)
                .build();
    }


    public FormRequest formRequest() {
        return FormRequest
                .builder()
                .phoneNumber(LOGOPED_PHONE_NUMBER)
                .logopedId(LOGOPED_ID)
                .description("Не выговаривает звук ль")
                .build();
    }
}