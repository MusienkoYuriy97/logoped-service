package by.logoped.logopedservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent extends User{
    @OneToMany
    private List<Task> task;
}
