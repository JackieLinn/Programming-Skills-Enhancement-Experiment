package ynu.jackielinn.lab4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ynu.jackielinn.lab4.dao.ProductDao;
import ynu.jackielinn.lab4.dao.PurchaseDao;
import ynu.jackielinn.lab4.dao.UserDao;
import ynu.jackielinn.lab4.entity.Product;
import ynu.jackielinn.lab4.entity.Purchase;
import ynu.jackielinn.lab4.entity.User;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class PurchaseDaoTest {

    @Resource
    private PurchaseDao purchaseDao;
    @Resource
    private UserDao userDao;
    @Resource
    private ProductDao productDao;

    @Test
    public void addPurchases() {
        User u = userDao.findByUsername("user1").orElseThrow();
        List<Product> products = productDao.findAll();

        Set<Product> pset = new HashSet<>();
        pset.add(products.get(0));
        pset.add(products.get(1));
        pset.add(products.get(2));

        Purchase pu = new Purchase();
        pu.setUser(u);
        pu.setProducts(pset);
        pu.setStatus(Purchase.Status.CREATED);
        pu.setCreateTime(LocalDateTime.now());

        purchaseDao.save(pu);
        System.out.println("订单ID=" + pu.getId());
    }

    @Test
    public void queryByUser() {
        User u = userDao.findByUsername("user1").orElseThrow();
        List<Purchase> list = purchaseDao.findByUserId(u.getId());
        System.out.println("user1订单数=" + list.size());
    }

    @Test
    public void updateStatus() {
        List<Purchase> list = purchaseDao.findByStatus(Purchase.Status.CREATED);
        if (!list.isEmpty()) {
            Long id = list.get(0).getId();
            purchaseDao.updateStatus(id, Purchase.Status.PAID);
            System.out.println(purchaseDao.findById(id).orElse(null));
        }
    }

    @Test
    public void customQueryContainProduct() {
        Product p = productDao.searchByName("product1").get(0);
        List<Purchase> res = purchaseDao.findPurchasesContainProduct(p.getId());
        System.out.println("包含product1的订单数=" + res.size());
    }

    @Test
    public void deletePurchase() {
        List<Purchase> list = purchaseDao.findAll();
        if (!list.isEmpty()) {
            purchaseDao.deleteById(list.get(list.size() - 1).getId());
        }
    }
}
