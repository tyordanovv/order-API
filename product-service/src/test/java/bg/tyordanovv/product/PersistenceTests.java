package bg.tyordanovv.product;

import bg.tyordanovv.core.product.ProductDTO;
import bg.tyordanovv.core.product.ProductType;
import bg.tyordanovv.product.persistence.ProductEntity;
import bg.tyordanovv.product.persistence.ProductRepository;
import bg.tyordanovv.product.service.ProductServiceController;
import bg.tyordanovv.requests.product.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
public class PersistenceTests extends MongoDbTestBase{

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceController productService;

    @Test
    void shouldSaveProduct() {
        ProductEntity product = new ProductEntity(
                "Product",
                "Description",
                ProductType.ELECTRONICS,
                10.0,
                1.0,
                10
        );
        Mono<ProductEntity> savedProductMono = productRepository.save(product);

        StepVerifier.create(savedProductMono)
                .expectNextMatches(p -> p.getProductId() != 0 && p.getName().equals(product.getName()))
                .verifyComplete();
    }

    @Test
    void shouldFindProductById() {
        ProductEntity product = new ProductEntity(
                "Product",
                "Description",
                ProductType.ELECTRONICS,
                10.0,
                1.0,
                10
        );
        Mono<ProductEntity> savedProductMono = productRepository.save(product);

        Mono<ProductEntity> foundProductMono = savedProductMono.flatMap(savedProduct -> productRepository.findByProductId(savedProduct.getProductId()));

        StepVerifier.create(foundProductMono)
                .expectNextMatches(foundProduct -> foundProduct.getName().equals(product.getName()))
                .verifyComplete();
    }

    @Test
    void shouldFindAllProducts() {
        ProductEntity product1 = new ProductEntity(
                "Product1",
                "Description1",
                ProductType.ELECTRONICS,
                10.0,
                1.0,
                10
        );
        ProductEntity product2 = new ProductEntity(
                "Product2",
                "Description2",
                ProductType.BOOKS,
                20.0,
                2.0,
                10
        );
        Flux<ProductEntity> savedProductsFlux = productRepository.saveAll(Arrays.asList(product1, product2));

        Flux<ProductEntity> foundProductsFlux = savedProductsFlux.thenMany(productRepository.findAll());

        StepVerifier.create(foundProductsFlux)
                .expectNextMatches(foundProduct -> foundProduct.getName().equals(product1.getName()))
                .expectNextMatches(foundProduct -> foundProduct.getName().equals(product2.getName()))
                .verifyComplete();
    }

    @Test
    void shouldDeleteProductById() {
        ProductEntity product = new ProductEntity(
                "Product",
                "Description",
                ProductType.ELECTRONICS,
                10.0,
                1.0,
                10
        );
        Mono<ProductEntity> savedProductMono = productRepository.save(product);

        Mono<Void> deletedProductMono = savedProductMono.flatMap(savedProduct -> productRepository.deleteByProductId(savedProduct.getProductId()));

        StepVerifier.create(deletedProductMono).verifyComplete();
    }
}
