package bg.tyordanovv.controller.product;

import bg.tyordanovv.requests.product.OrderedProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductQuantity {

    @PostMapping("/api/v1/ordered-product")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void editProductQuantity(@RequestBody List<OrderedProductDTO> productList);
}
