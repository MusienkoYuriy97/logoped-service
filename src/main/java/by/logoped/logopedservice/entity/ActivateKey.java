package by.logoped.logopedservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.*;

@Schema(name = "ActivateKey(Unuseful in Controller)")
@Entity
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "activate_key")
public class ActivateKey {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Schema(example = "1")
    private Long id;
    @Column(name = "simple_key", nullable = false)
    @Schema(example = "sdf13Fas34f2")
    private String simpleKey;
    @OneToOne
    private User user;
}
