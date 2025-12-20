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
@Table(name = "t_user")
@Schema(name = "User", description = "统一用户表：管理员/学生用Role区分")
public class User {

    public enum Role {
        ADMIN, STUDENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @Schema(description = "用户名（唯一）")
    private String username;

    @Column(nullable = false, length = 60)
    @Schema(description = "密码")
    private String password;

    @Column(unique = true, nullable = false, length = 120)
    @Schema(description = "邮箱（唯一）")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "角色：ADMIN/STUDENT")
    private Role role;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
