package ynu.jackielinn.lab4.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "t_product")
@Schema(name = "Product", description = "商品实体")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "商品名")
    private String name;

    @Column(nullable = false)
    @Schema(description = "价格")
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(description = "库存")
    private Integer stock;

    @Column(length = 200)
    @Schema(description = "分类")
    private String category;
}
