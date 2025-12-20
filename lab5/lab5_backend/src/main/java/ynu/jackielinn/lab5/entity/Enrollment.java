package ynu.jackielinn.lab5.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "t_enrollment",
        uniqueConstraints = @UniqueConstraint(name = "uk_student_course", columnNames = {"student_id", "course_id"}))
@Schema(name = "Enrollment", description = "选课关系：一个学生选一门课")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    @Schema(description = "学生")
    private User student;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    @Schema(description = "课程")
    private Course course;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
