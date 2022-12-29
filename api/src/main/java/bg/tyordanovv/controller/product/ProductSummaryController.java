package bg.tyordanovv.controller.product;

import bg.tyordanovv.responses.product.ProductSummaryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductSummaryController {
    @GetMapping(
            value = "/api/v1/product/{productId}",
            produces = "application/json")
    ProductSummaryResponse getProductByID(@PathVariable("productId") Long productId);
}
