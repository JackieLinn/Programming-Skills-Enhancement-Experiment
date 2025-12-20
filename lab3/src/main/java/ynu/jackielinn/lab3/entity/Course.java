package ynu.jackielinn.lab3.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 课程实体类
 */
@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;      // 课程名称
    @Column(nullable = false, length = 50)
    private String teacher;   // 授课教师
}
