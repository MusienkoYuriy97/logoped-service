package by.logoped.logopedservice.mapper;

import by.logoped.logopedservice.dto.RegistrationRequest;
import by.logoped.logopedservice.dto.RegistrationResponse;
import by.logoped.logopedservice.entity.Role;
import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.util.UserStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("userMapper")
public class UserMapperImpl implements UserMapper{
    @Override
    public RegistrationResponse toDto(User user) {
        Collection<Role> userRoles = user.getUserRoles();
        List<String> roles= userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());
        return RegistrationResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userStatus(user.getUserStatus())
                .roles(roles)
                .build();
    }

    @Override
    public User toUser(RegistrationRequest registrationRequest) {
        return User
                .builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(registrationRequest.getPassword())
                .build();
    }
}
