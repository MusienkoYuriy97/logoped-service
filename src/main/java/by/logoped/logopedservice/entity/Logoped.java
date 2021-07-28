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

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "logoped")
@Schema(name = "Logoped(Unuseful in Controller)")
public class Logoped {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Schema(example = "1")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "education", nullable = false)
    @Schema(example = "БГТУ")
    private String education;

    @OneToMany(fetch = EAGER)
    @JoinTable(
            name = "logoped_category",
            joinColumns = @JoinColumn(
                    name = "logoped_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "id"
            ))
    private Set<Category> categories;

    @Column(name = "work_place")
    @Schema(example = "Детский сад № 1")
    private String workPlace;

    @Column(name = "work_experience")
    @Schema(example = "4")
    private int workExperience;
}