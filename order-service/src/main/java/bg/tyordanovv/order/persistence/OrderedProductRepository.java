package bg.tyordanovv.order.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderedProductRepository extends CrudRepository<OrderedProductEntity, Long> {

    @Transactional(readOnly = true)
    @Query("select o from OrderedProductEntity o where o.order_id = ?1")
    List<OrderedProductEntity> findAllByOrderId(Long orderId);

    @Transactional(readOnly = true)
    @Query("select o from OrderedProductEntity o where o.product_id = ?1")
    List<OrderedProductEntity> findAllByProductId(Long productId);

    @Transactional
    @Modifying
    @Query("delete from OrderedProductEntity o where o.order_id = ?1")
    void deleteAllByOrderId(Long orderId);

    @Transactional
    @Modifying
    @Query("delete from OrderedProductEntity o where o.id = ?1")
    void deleteAllByProductId(Long productId);
}
