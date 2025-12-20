package ynu.jackielinn.lab4.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 订单实体
 */
@Data
@Entity
@Table(name = "t_purchase")
@Schema(name = "Purchase", description = "订单实体")
public class Purchase {

    // 订单状态枚举
    public enum Status {
        CREATED, PAID, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    // 多对一：多个订单 -> 一个用户
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "下单用户")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "订单状态")
    private Status status;

    @Column(name = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 多对多：订单 <-> 商品（通过中间表 t_purchase_product 关联）
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_purchase_product",           // 中间表名
            joinColumns = @JoinColumn(name = "purchase_id"),        // 当前实体外键
            inverseJoinColumns = @JoinColumn(name = "product_id")   // 关联实体外键
    )
    @Schema(description = "订单商品列表")
    private Set<Product> products = new HashSet<>();
}
