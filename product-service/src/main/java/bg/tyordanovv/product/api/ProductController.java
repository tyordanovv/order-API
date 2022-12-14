package bg.tyordanovv.product.api;

import bg.tyordanovv.clients.product.ProductQuantityRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ProductController {

    @PostMapping("/api/v1/product/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> editProductQuantity(@RequestBody(required = false) List<ProductQuantityRequest> productList);

    @GetMapping("/api/v1/product/category/{category}")
    Flux<ProductSummary> getProductByCategory(@PathVariable("category") String category);

    @GetMapping("/api/v1/product/{productId}")
    Mono<ProductSummary> getProductByID(@PathVariable("productId") Long productId);

    @PostMapping(
            value = "/api/v1/product/create",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> createProduct(@RequestBody(required = false) CreateProductRequest request);

    @PostMapping(value = "/api/v1/product/delete/{productId}")
    Mono<Void> deleteProduct(@PathVariable("productId") Long productId);
}
