package bg.tyordanovv.product.persistence;

import bg.tyordanovv.core.product.ProductType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, String> {

    Mono<ProductEntity> findByProductId(Long productId);

    Flux<ProductEntity> findByType(ProductType type);

    Mono<Void> deleteByProductId(Long productId);
}
