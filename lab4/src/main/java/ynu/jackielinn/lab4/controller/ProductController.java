package ynu.jackielinn.lab4.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import ynu.jackielinn.lab4.entity.Product;
import ynu.jackielinn.lab4.service.ProductService;

import jakarta.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "商品管理", description = "商品CRUD与库存自定义操作")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping
    @Operation(summary = "新增商品", description = "创建商品")
    public Product create(@RequestBody Product p) {
        return productService.add(p);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询商品", description = "按ID查询")
    public Product get(@PathVariable Long id) {
        return productService.find(id).orElse(null);
    }

    @GetMapping("/range")
    @Operation(summary = "价格区间查询", description = "findByPriceBetween示例")
    public List<Product> range(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return productService.range(min, max);
    }

    @PutMapping("/{id}/stock/decrease")
    @Operation(summary = "减少库存", description = "通过@Modifying原子扣库存")
    public int decrease(@PathVariable Long id, @RequestParam int delta) {
        return productService.decreaseStock(id, delta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品", description = "按ID删除")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
