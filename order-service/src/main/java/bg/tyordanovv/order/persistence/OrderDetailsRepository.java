package bg.tyordanovv.order.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {
    @Transactional(readOnly = true)
    Optional<OrderDetailsEntity> findById(Long id);

//    @Transactional(readOnly = true)
//    @Query("select o from OrderedProductEntity o where o.order_id = ?1")
//    List<OrderedProductEntity> findAllByOrderId(Long orderId);
//
//    @Transactional(readOnly = true)
//    @Query("select o from OrderedProductEntity o where o.product_id = ?1")
//    List<OrderedProductEntity> findAllByProductId(Long productId);
//
//    @Transactional
//    @Modifying
//    @Query("delete from OrderedProductEntity o where o.order_id = ?1")
//    void deleteAllByOrderId(Long orderId);
//
//    @Transactional
//    @Modifying
//    @Query("delete from OrderedProductEntity o where o.id = ?1")
//    void deleteAllByProductId(Long productId);
}
