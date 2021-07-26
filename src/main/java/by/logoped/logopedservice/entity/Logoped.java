package by.logoped.logopedservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logoped")
public class Logoped extends User {
    @OneToMany
    private List<Task> task;
}
