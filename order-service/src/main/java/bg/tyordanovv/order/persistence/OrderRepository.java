package bg.tyordanovv.order.persistence;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
