package bg.tyordanovv.product.service;

import bg.tyordanovv.controller.product.ProductController;
import bg.tyordanovv.controller.product.ProductSummaryController;
import bg.tyordanovv.requests.product.CreateProductRequest;
import bg.tyordanovv.requests.product.ProductQuantityRequest;
import bg.tyordanovv.responses.product.ProductSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ProductService implements ProductController, ProductSummaryController {

    @Override
    public List<ProductSummaryResponse> getProductByCategory(String category) {
        log.info("product summary list returned");
        return null;
    }

    @Override
    public ProductSummaryResponse getProductByID(Long productId) {
        log.info("product summary returned");

//        return null;
        return new ProductSummaryResponse(1L, "burger", 5.5, "burger description", "food", 4.4, 2);
    }

    @Override
    public void createProduct(CreateProductRequest request) {
        log.info("product created");
//        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        log.info("product deleted");
//        return null;
    }

    @Override
    public void editProduct(Long productId) {

    }
}
