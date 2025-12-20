package ynu.jackielinn.lab3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生实体类
 * @Entity 标记为 JPA 实体
 * @Table 指定映射的数据库表名
 */
@Data
@Entity
@Table(name = "student")
public class Student {
    @Id  // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自增策略
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 10)
    private String sex;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(name = "create_time")  // 指定列名（驼峰 -> 下划线）
    private LocalDateTime createTime;
}
