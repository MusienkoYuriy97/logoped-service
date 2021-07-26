package by.logoped.logopedservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private Logoped user;
    @ManyToOne
    private Parent adult;
}
