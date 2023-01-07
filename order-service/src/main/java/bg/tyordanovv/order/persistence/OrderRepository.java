package bg.tyordanovv.order.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Transactional(readOnly = true)
    List<OrderEntity> findByEmail(String email);

    @Transactional(readOnly = true)
    List<OrderEntity> findByNumber(String number);

    @Transactional(readOnly = true)
    Optional<OrderEntity> findById(Long id);
}
