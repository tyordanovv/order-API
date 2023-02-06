package bg.tyordanovv.order.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {
    @Transactional(readOnly = true)
    Optional<OrderDetailsEntity> findById(Long id);

    @Transactional(readOnly = true)
    Set<OrderDetailsEntity> findByOrderId(OrderEntity order);

}
