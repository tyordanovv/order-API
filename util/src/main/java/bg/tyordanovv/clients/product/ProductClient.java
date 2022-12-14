package bg.tyordanovv.clients.product;

import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveFeignClient(
        name = "product",
        url = "http://localhost:8083"
)
public interface ProductClient {
    @PostMapping("/api/v1/product/edit")
    Mono<Void> editProductQuantity(List<ProductQuantityRequest> productList);
}
