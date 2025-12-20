package ynu.jackielinn.lab4.dto;

import lombok.Data;

import java.util.Set;

/**
 * 创建订单请求 DTO
 */
@Data
public class CreatePurchaseRequest {
    private Long userId;           // 下单用户ID
    private Set<Long> productIds;  // 商品ID集合
}
