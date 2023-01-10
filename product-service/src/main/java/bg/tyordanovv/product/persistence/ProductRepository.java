package bg.tyordanovv.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Transactional(readOnly = true)
    ProductEntity findByProductId(Long id);
}
