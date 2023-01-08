package bg.tyordanovv.delivery.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByOrderId(Long orderId);

    Optional<List<DeliveryEntity>> findAllByOrderId(Long orderId);
}
