package ynu.jackielinn.lab5.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EnrollmentView {
    private Long enrollmentId;

    private Long studentId;
    private String studentUsername;

    private Long courseId;
    private String courseName;

    private LocalDateTime createTime;
}
