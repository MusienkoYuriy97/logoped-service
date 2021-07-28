package by.logoped.logopedservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "form")
@Schema(name = "Form(Unuseful in Controller)")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "logoped_id")
    private Logoped logoped;

    @Column(name = "phone_number")
    @Schema(example = "+375296213287")
    private String phoneNumber;

    @Column(name = "description")
    @Schema(name = "Не выговаривает звук Ль")
    private String description;
}