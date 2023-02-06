package bg.tyordanovv.order.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Transactional(readOnly = true)
    Set<OrderEntity> findByEmail(String email);

    @Transactional(readOnly = true)
    Set<OrderEntity> findByNumber(String number);

    @Transactional(readOnly = true)
    Optional<OrderEntity> findById(Long id);
}
