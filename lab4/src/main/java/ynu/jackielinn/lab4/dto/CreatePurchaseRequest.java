package ynu.jackielinn.lab4.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreatePurchaseRequest {
    private Long userId;
    private Set<Long> productIds;
}
