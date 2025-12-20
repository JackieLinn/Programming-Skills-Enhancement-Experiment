package ynu.jackielinn.lab4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ynu.jackielinn.lab4.dao.ProductDao;
import ynu.jackielinn.lab4.entity.Product;

import jakarta.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class ProductDaoTest {

    @Resource
    private ProductDao productDao;

    @Test
    public void addProducts() {
        String[] cats = {"Book", "Food", "Digital"};
        for (int i = 1; i <= 6; i++) {
            Product p = new Product();
            p.setName("product" + i);
            p.setPrice(new BigDecimal("10").add(new BigDecimal(i * 3)));
            p.setStock(100 + i);
            p.setCategory(cats[i % cats.length]);
            productDao.save(p);
        }
    }

    @Test
    public void queryRange() {
        List<Product> list = productDao.findByPriceBetween(new BigDecimal("10"), new BigDecimal("30"));
        list.forEach(System.out::println);
    }

    @Test
    public void decreaseStock() {
        Product p = productDao.searchByName("product1").get(0);
        int ok = productDao.decreaseStock(p.getId(), 5);
        System.out.println("扣库存结果：" + ok);
        System.out.println(productDao.findById(p.getId()).orElse(null));
    }

    @Test
    public void deleteProduct() {
        List<Product> list = productDao.searchByName("product6");
        if (!list.isEmpty()) {
            productDao.deleteById(list.get(0).getId());
        }
    }
}
