package by.logoped.logopedservice.mapper;

import by.logoped.logopedservice.dto.RegistrationRequest;
import by.logoped.logopedservice.dto.RegistrationResponse;
import by.logoped.logopedservice.entity.User;

public interface UserMapper {
    RegistrationResponse toDto(User user);
    User toUser(RegistrationRequest registrationRequest);
}
