package by.logoped.logopedservice.service;

import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.exception.NoActivatedAccountException;
import by.logoped.logopedservice.exception.UserDataException;
import by.logoped.logopedservice.exception.UserNotFoundException;
import by.logoped.logopedservice.repository.UserRepository;
import by.logoped.logopedservice.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Load user by email {}", email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else if (UserStatus.BLOCKED.equals(optionalUser.get().getUserStatus())){
            log.error("User not active");
            throw new NoActivatedAccountException("User not active");
        }else {
            log.info("User found in the database: {}", email);
            User user = optionalUser.get();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getUserRoles().forEach(userRole -> {
                authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
            });

            return optionalUser.get();
        }
    }

    public static String getCurrentEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }

        throw new UserNotFoundException("User not found");
    }

    public static User getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            return  (User) authentication.getPrincipal();
        }
        throw new UserNotFoundException("User not found");
    }
}
