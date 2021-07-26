package by.logoped.logopedservice.service;

import by.logoped.logopedservice.entity.User;
import by.logoped.logopedservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database: {}", email);
            User user = optionalUser.get();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getUserRoles().forEach(userRole -> {
                authorities.add(new SimpleGrantedAuthority(userRole.getName()));
            });
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        }
    }
}
