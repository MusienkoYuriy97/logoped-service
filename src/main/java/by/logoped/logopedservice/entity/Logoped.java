package by.logoped.logopedservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Schema(name = "Logoped(Unuseful in Controller)")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "logoped")
public class Logoped {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Schema(example = "1")
    private Long id;

    @OneToOne
    private User user;
    @Column(name = "education", nullable = false)
    private String education;

    @OneToMany(fetch = EAGER)
    private Set<Category> categories;
}
