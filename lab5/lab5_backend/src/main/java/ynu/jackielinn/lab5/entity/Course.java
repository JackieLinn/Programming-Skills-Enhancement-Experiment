package ynu.jackielinn.lab5.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "t_course")
@Schema(name = "Course", description = "课程")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "课程名")
    private String name;

    @Column(nullable = false, length = 50)
    @Schema(description = "授课教师")
    private String teacher;

    @Column(nullable = false)
    @Schema(description = "学分")
    private Integer credit;
}
