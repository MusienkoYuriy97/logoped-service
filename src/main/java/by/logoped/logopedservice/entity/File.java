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
@Table(name = "file")
@Schema(name = "File")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(example = "1")
    private Long id;

    @Column(name = "file_name", nullable = false)
    @Schema(example = "filename")
    private String fileName;

    @Column(name = "download_link", nullable = false)
    @Schema(example = "https://firebasestorage.googleapis.com/v0/b/logoped-service/o/%s?alt=media")
    private String downloadLink;

    @ManyToOne
    @JoinColumn(name = "logoped_id")
    private Logoped logoped;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
