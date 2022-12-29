package bg.tyordanovv.order.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantityEntity, Long> {
    @Transactional(readOnly = true)
    Optional<ProductQuantityEntity> findById(Long id);
}
