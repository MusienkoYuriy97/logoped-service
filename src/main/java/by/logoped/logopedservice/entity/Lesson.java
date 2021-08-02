package by.logoped.logopedservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "lesson")
@Schema(name = "Lesson(Unuseful in Controller)")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(example = "1")
    private Long id;

    @Column(name = "start_time", nullable = false)
    @Schema(example = "2021-09-01T10:00:00")
    private LocalDateTime startTime;

    @Column(name = "address", nullable = true)
    @Schema(example = "Слободская 163 кв 42")
    private String address;

    @Column(name = "zoom_link", nullable = true)
    @Schema(example = "zoomlink")
    private String zoomlink;

    @ManyToOne
    @JoinColumn(name = "logoped_id")
    private Logoped logoped;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
