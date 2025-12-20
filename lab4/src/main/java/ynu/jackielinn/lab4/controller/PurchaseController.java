package ynu.jackielinn.lab4.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import ynu.jackielinn.lab4.dto.CreatePurchaseRequest;
import ynu.jackielinn.lab4.entity.Purchase;
import ynu.jackielinn.lab4.service.PurchaseService;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 订单 REST API 控制器
 */
@RestController
@RequestMapping("/api/purchases")
@Tag(name = "订单管理", description = "订单CRUD与自定义查询")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;

    @PostMapping
    @Operation(summary = "创建订单", description = "用户+商品集合创建订单")
    public Purchase create(@RequestBody CreatePurchaseRequest req) {
        return purchaseService.create(req.getUserId(), req.getProductIds());
    }

    @GetMapping("/user/{userId}")  // GET /api/purchases/user/{userId}
    @Operation(summary = "按用户查询订单", description = "findByUserId示例")
    public List<Purchase> listByUser(@PathVariable Long userId) {
        return purchaseService.byUser(userId);
    }

    @PutMapping("/{id}/status")  // PUT /api/purchases/{id}/status?status=PAID
    @Operation(summary = "更新订单状态", description = "@Modifying更新状态")
    public int updateStatus(@PathVariable Long id, @RequestParam Purchase.Status status) {
        return purchaseService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单", description = "按ID删除")
    public void delete(@PathVariable Long id) {
        purchaseService.delete(id);
    }
}
