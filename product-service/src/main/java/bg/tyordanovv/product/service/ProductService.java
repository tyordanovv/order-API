package bg.tyordanovv.product.service;

import bg.tyordanovv.clients.product.ProductQuantityRequest;
import bg.tyordanovv.product.api.CreateProductRequest;
import bg.tyordanovv.product.api.ProductController;
import bg.tyordanovv.product.api.ProductSummary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ProductService implements ProductController {
    @Override
    public Mono<Void> editProductQuantity(List<ProductQuantityRequest> productList) {
        return null;
    }

    @Override
    public Flux<ProductSummary> getProductByCategory(String category) {
        return null;
    }

    @Override
    public Mono<ProductSummary> getProductByID(Long productId) {
        return null;
    }

    @Override
    public Mono<Void> createProduct(CreateProductRequest request) {
        return null;
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return null;
    }
}
