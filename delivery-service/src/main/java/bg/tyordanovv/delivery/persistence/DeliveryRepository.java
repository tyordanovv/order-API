package bg.tyordanovv.delivery.persistence;

import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {
}
