package by.logoped.logopedservice.mapper;

import by.logoped.logopedservice.dto.LogopedInfoResponse;
import by.logoped.logopedservice.dto.RegistrationLogopedRequest;
import by.logoped.logopedservice.dto.RegistrationUserRequest;
import by.logoped.logopedservice.dto.RegistrationResponse;
import by.logoped.logopedservice.entity.Logoped;
import by.logoped.logopedservice.entity.User;

public interface ObjectMapper {
    RegistrationResponse toUserResponse(User user);
    User toUser(RegistrationUserRequest request);
    Logoped toLogoped(RegistrationLogopedRequest request);
    LogopedInfoResponse toLogopedResponse(Logoped logoped);
}
