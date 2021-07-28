package by.logoped.logopedservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "category")
@Schema(name = "Category(Unuseful in Controller)")
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    @Schema(example = "1")
    private Long id;


    @Column(name = "category_name", nullable = false)
    @Schema(example = "Подготовка к школе")
    private String categoryName;
}
