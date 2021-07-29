package by.logoped.logopedservice.service;

import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.repository.UserRepository;
import by.logoped.logopedservice.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.logoped.logopedservice.util.UserStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public void ban(Long userId) {
        log.info("Ban a user {}", userId);
        setUserStatus(userId, BANNED);
    }

    public void unBan(Long userId) {
        log.info("Unban a user {}", userId);
        setUserStatus(userId, ACTIVE);
    }

    private void setUserStatus(Long userId, UserStatus userStatus) {
        final User currentUser = UserDetailsServiceImpl.getCurrentUser();
        if (userId.equals(currentUser.getId())){
            throw new UserDataException("You cannot change status for yourself");
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserStatus(userStatus);
            userRepository.save(user);
        }else {
            log.error("User not found in database");
            throw new UserNotFoundException("User not found");
        }
    }
}
