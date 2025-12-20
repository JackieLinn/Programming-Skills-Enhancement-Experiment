package ynu.jackielinn.lab4.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import ynu.jackielinn.lab4.dao.ProductDao;
import ynu.jackielinn.lab4.entity.Product;

import jakarta.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 商品业务层
 */
@Service
@Transactional
public class ProductService {

    @Resource
    private ProductDao productDao;

    public Product add(Product p) {
        return productDao.save(p);
    }

    /** 扣减库存（返回影响行数，0 表示库存不足） */
    public int decreaseStock(Long id, int delta) {
        return productDao.decreaseStock(id, delta);
    }

    public Optional<Product> find(Long id) {
        return productDao.findById(id);
    }

    /** 价格区间查询 */
    public List<Product> range(BigDecimal min, BigDecimal max) {
        return productDao.findByPriceBetween(min, max);
    }

    public void delete(Long id) {
        productDao.deleteById(id);
    }
}
