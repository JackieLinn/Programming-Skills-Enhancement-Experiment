package ynu.jackielinn.lab5.dto;

import lombok.Data;

@Data
public class UpdateCourseRequest {
    private String name;
    private String teacher;
    private Integer credit;
}
