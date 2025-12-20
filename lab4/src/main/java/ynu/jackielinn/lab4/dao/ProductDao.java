package ynu.jackielinn.lab4.dao;

import ynu.jackielinn.lab4.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品 DAO
 */
public interface ProductDao extends IBaseDao<Product, Long> {

    // 派生查询：按分类查询
    List<Product> findByCategory(String category);

    // 派生查询：价格区间查询（Between 关键字）
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("select p from Product p where p.name like %:kw% order by p.price asc")
    List<Product> searchByName(@Param("kw") String keyword);

    // 原子扣库存：stock >= delta 才执行，防止超卖
    @Modifying
    @Transactional
    @Query("update Product p set p.stock = p.stock - :delta where p.id = :id and p.stock >= :delta")
    int decreaseStock(@Param("id") Long id, @Param("delta") int delta);
}
