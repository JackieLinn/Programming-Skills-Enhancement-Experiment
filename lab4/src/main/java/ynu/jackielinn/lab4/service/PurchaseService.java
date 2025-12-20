package ynu.jackielinn.lab4.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

/**
 * 订单业务层
 */
@Service
@Transactional
public class PurchaseService {

    @Resource
    private PurchaseDao purchaseDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ProductDao productDao;

    /**
     * 创建订单
     * @param userId 用户ID
     * @param productIds 商品ID集合
     */
    public Purchase create(Long userId, Set<Long> productIds) {
        User user = userDao.findById(userId).orElseThrow();  // 用户不存在则抛异常
        Set<Product> products = new HashSet<>(productDao.findAllById(productIds));

        Purchase pu = new Purchase();
        pu.setUser(user);
        pu.setProducts(products);
        pu.setStatus(Purchase.Status.CREATED);
        pu.setCreateTime(LocalDateTime.now());

        return purchaseDao.save(pu);
    }

    public int updateStatus(Long purchaseId, Purchase.Status status) {
        return purchaseDao.updateStatus(purchaseId, status);
    }

    public List<Purchase> byUser(Long userId) {
        return purchaseDao.findByUserId(userId);
    }

    public void delete(Long id) {
        purchaseDao.deleteById(id);
    }
}
