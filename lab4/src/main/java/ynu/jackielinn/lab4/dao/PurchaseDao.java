package ynu.jackielinn.lab4.dao;

import ynu.jackielinn.lab4.entity.Purchase;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PurchaseDao extends IBaseDao<Purchase, Long> {

    List<Purchase> findByUserId(Long userId);

    List<Purchase> findByStatus(Purchase.Status status);

    @Query("select pu from Purchase pu join pu.products p where p.id = :pid")
    List<Purchase> findPurchasesContainProduct(@Param("pid") Long productId);

    @Modifying
    @Transactional
    @Query("update Purchase pu set pu.status = :st where pu.id = :id")
    int updateStatus(@Param("id") Long purchaseId, @Param("st") Purchase.Status status);
}
