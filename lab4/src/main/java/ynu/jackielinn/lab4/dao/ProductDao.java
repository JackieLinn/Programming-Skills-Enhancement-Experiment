package ynu.jackielinn.lab4.dao;

import ynu.jackielinn.lab4.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao extends IBaseDao<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("select p from Product p where p.name like %:kw% order by p.price asc")
    List<Product> searchByName(@Param("kw") String keyword);

    @Modifying
    @Transactional
    @Query("update Product p set p.stock = p.stock - :delta where p.id = :id and p.stock >= :delta")
    int decreaseStock(@Param("id") Long id, @Param("delta") int delta);
}
