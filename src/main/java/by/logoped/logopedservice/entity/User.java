package by.logoped.logopedservice.entity;

import by.logoped.logopedservice.util.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
@Schema(name = "User(Unuseful in Controller)")
public class User implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Yuriy")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Schema(example = "Musienko")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Schema(example = "97musienko@gmail.com")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Schema(example = "+375298344491")
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Schema(example = "12345")
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(example = "ACTIVE")
    @Enumerated(STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "users_user_role",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_role_id",
                    referencedColumnName = "id"
            ))
    private Collection<Role> userRoles = new ArrayList<>();

    @Override
    @Schema(hidden = true)
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        getUserRoles().forEach(userRole -> {
            authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        });
        return authorities;
    }

    @Override
    @Schema(hidden = true)
    @JsonIgnore
    public String getUsername() {
        return getEmail();
    }

    @Override
    @Schema(hidden = true)
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return UserStatus.ACTIVE.equals(userStatus);    }

    @Override
    @Schema(hidden = true)
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return UserStatus.ACTIVE.equals(userStatus);    }

    @Override
    @Schema(hidden = true)
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return UserStatus.ACTIVE.equals(userStatus);
    }

    @Override
    @Schema(hidden = true)
    @JsonIgnore
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(userStatus);
    }
}