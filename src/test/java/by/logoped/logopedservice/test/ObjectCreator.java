package by.logoped.logopedservice.test;

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

import static by.logoped.logopedservice.test.UserConstant.*;
import static by.logoped.logopedservice.util.UserStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class ObjectCreator {
    @Value("${role.user}")
    private String userRole;
    @Value("${jwt.prefix}")
    private String prefix;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public User activeUser(){
        Collection<Role> roles = new ArrayList<>();
        roles.add(new Role(1l,userRole));
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

    public void buildSecurityContext(){
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(USER_EMAIL, USER_PASSWORD);
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
}
