package bg.tyordanovv.controller.product;

import bg.tyordanovv.requests.product.OrderedProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductQuantityController {

    @PostMapping("/api/v1/product/order/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> editProductQuantity(@RequestBody List<OrderedProductDTO> productList);
}
