package bg.tyordanovv.controller.product;

import bg.tyordanovv.requests.product.*;
import bg.tyordanovv.responses.product.ProductSummaryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ProductController extends ProductQuantity {

    @GetMapping("/api/v1/product/category/{category}")
    List<ProductSummaryResponse> getProductByCategory(@PathVariable("category") String category);

    @PostMapping(
            value = "/api/v1/product/create",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void createProduct(@RequestBody(required = false) CreateProductRequest request);

    @PostMapping(value = "/api/v1/product/delete/{productId}")
    Mono<Void> deleteProduct(@PathVariable("productId") Long productId);

    @PostMapping(value = "/api/v1/product/edit/{productId}")
    void editProduct(@PathVariable("productId") Long productId);

    @GetMapping(
            value = "/api/v1/product/{productId}",
            produces = "application/json")
    ProductSummaryResponse getProductSummaryByID(@PathVariable("productId") Long productId);


}
