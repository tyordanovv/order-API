package bg.tyordanovv.product.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, String> {
    @Transactional(readOnly = true)
    Mono<ProductEntity> findByProductId(Long productId);
}
