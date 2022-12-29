package bg.tyordanovv.controller.product;

import bg.tyordanovv.requests.product.ProductQuantityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface ProductQuantity {

    @PostMapping("/api/v1/ordered-product")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void editProductQuantity(@RequestBody List<ProductQuantityRequest> productList);
}
