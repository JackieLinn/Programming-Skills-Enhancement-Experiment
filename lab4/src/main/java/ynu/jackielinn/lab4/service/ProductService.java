package ynu.jackielinn.lab4.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import ynu.jackielinn.lab4.dao.ProductDao;
import ynu.jackielinn.lab4.entity.Product;

import jakarta.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Resource
    private ProductDao productDao;

    public Product add(Product p) {
        return productDao.save(p);
    }

    public int decreaseStock(Long id, int delta) {
        return productDao.decreaseStock(id, delta);
    }

    public Optional<Product> find(Long id) {
        return productDao.findById(id);
    }

    public List<Product> range(BigDecimal min, BigDecimal max) {
        return productDao.findByPriceBetween(min, max);
    }

    public void delete(Long id) {
        productDao.deleteById(id);
    }
}
