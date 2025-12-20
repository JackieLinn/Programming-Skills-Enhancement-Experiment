package ynu.jackielinn.lab4.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 * @Schema 用于 Swagger/Knife4j API 文档展示
 */
@Data
@Entity
@Table(name = "t_user")
@Schema(name = "User", description = "用户实体")
public class User {

    // 枚举类型：用户角色
    public enum Role {
        ADMIN, CUSTOMER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)  // unique 约束
    @Schema(description = "用户名（唯一）")
    private String username;

    @Column(nullable = false, length = 60)
    @Schema(description = "密码")
    private String password;

    @Column(unique = true, nullable = false, length = 120)
    @Schema(description = "邮箱（唯一）")
    private String email;

    @Enumerated(EnumType.STRING)  // 枚举存储为字符串
    @Column(nullable = false, length = 20)
    @Schema(description = "角色")
    private Role role;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
