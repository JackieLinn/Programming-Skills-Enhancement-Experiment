package ynu.jackielinn.lab3.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 10)
    private String sex;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
