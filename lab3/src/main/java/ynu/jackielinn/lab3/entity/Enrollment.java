package ynu.jackielinn.lab3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 选课记录实体（关联表，实现学生与课程的多对多关系）
 * uniqueConstraints: 学生+课程 组合唯一，防止重复选课
 */
@Data
@Entity
@Table(name = "enrollment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 多对一：多条选课记录 -> 一个学生
    @ManyToOne(optional = false, fetch = FetchType.LAZY)  // LAZY 延迟加载
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // 多对一：多条选课记录 -> 一门课程
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column
    private Double score;         // 成绩
    @Column(name = "enroll_time")
    private LocalDateTime enrollTime;  // 选课时间
}
