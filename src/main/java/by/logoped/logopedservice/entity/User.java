package by.logoped.logopedservice.entity;

import by.logoped.logopedservice.util.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(STRING)
    private UserStatus userStatus;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> userRoles = new ArrayList<>();
}