package bg.tyordanovv.controller.product;

import bg.tyordanovv.core.product.ProductDTO;
import bg.tyordanovv.core.product.ProductType;
import bg.tyordanovv.requests.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/products/")
public interface ProductControllerController extends ProductQuantityController {

    @GetMapping("category/{category}")
    Flux<ProductDTO> getProductByCategory(@PathVariable("category") ProductType category);

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<ProductDTO> createProduct(@RequestBody CreateProductRequest request);

    @DeleteMapping(value = "{productId}")
    Mono<Void> deleteProduct(@PathVariable("productId") Long productId);

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> editProduct(@RequestBody ProductDTO productDTO);

    @GetMapping(
            value = "{productId}",
            produces = "application/json")
    Mono<ProductDTO> getProductDTOByID(@PathVariable("productId") Long productId);


}
