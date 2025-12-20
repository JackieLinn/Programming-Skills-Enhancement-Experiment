package ynu.jackielinn.lab5.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 选课视图 DTO
 * 扁平化展示选课信息，避免实体嵌套导致的序列化问题
 */
@Data
@Builder
public class EnrollmentView {
    private Long enrollmentId;

    // 学生信息
    private Long studentId;
    private String studentUsername;

    // 课程信息
    private Long courseId;
    private String courseName;
    private String teacher;
    private Integer credit;

    private LocalDateTime createTime;
}
