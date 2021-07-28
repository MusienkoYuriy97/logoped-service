package by.logoped.logopedservice.mapper;

import by.logoped.logopedservice.dto.response.LogopedInfoResponse;
import by.logoped.logopedservice.dto.request.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.request.RegistrationUserRequest;
import by.logoped.logopedservice.dto.response.RegistrationResponse;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.Role;
import by.logoped.logopedservice.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("userMapper")
public class ObjectMapperImpl implements ObjectMapper {
    @Override
    public RegistrationResponse toUserResponse(User user) {
        Collection<Role> userRoles = user.getUserRoles();
        List<String> roles= userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());
        return RegistrationResponse
                .builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .userStatus(user.getUserStatus())
                    .roles(roles)
                .build();
    }

    @Override
    public User toUser(RegistrationUserRequest request) {
        return User
                .builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .password(request.getPassword())
                .build();
    }

    @Override
    public Logoped toLogoped(RegistrationLogopedRequest request) {
        return Logoped
                .builder()
                    .education(request.getEducation())
                    .workPlace(request.getWorkPlace())
                    .workExperience(request.getWorkExperience())
                .build();
    }

    @Override
    public LogopedInfoResponse toLogopedResponse(Logoped logoped) {
        Set<String> categories = new HashSet<>();
        logoped.getCategories().forEach(category -> categories.add(category.getCategoryName()));

        return LogopedInfoResponse
                .builder()
                    .firstName(logoped.getUser().getFirstName())
                    .lastName(logoped.getUser().getLastName())
                    .email(logoped.getUser().getEmail())
                    .phoneNumber(logoped.getUser().getPhoneNumber())
                    .education(logoped.getEducation())
                    .category(categories)
                .build();
    }
}