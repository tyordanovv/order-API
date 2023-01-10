package bg.tyordanovv.product.service;

import bg.tyordanovv.controller.product.ProductController;
import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.product.persistence.ProductEntity;
import bg.tyordanovv.product.persistence.ProductRepository;
import bg.tyordanovv.requests.product.CreateProductRequest;
import bg.tyordanovv.requests.product.OrderedProductDTO;
import bg.tyordanovv.responses.product.ProductSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ProductService implements ProductController {
    private final ProductRepository repository;

    @Autowired
    public ProductService(
            ProductRepository repository
    ){
        this.repository = repository;
    }

    @Override
    public List<ProductSummaryResponse> getProductByCategory(String category) {
        log.info("product summary list returned");
        return null;
    }

    @Override
    public void createProduct(CreateProductRequest request) {
        try {
            ProductEntity product = new ProductEntity(
                    request.name(),
                    request.description(),
                    request.type(),
                    request.price(),
                    request.weight(),
                    request.quantity()
            );
            repository.save(product);
            log.info("Successfully added new product");
        }
        catch (RuntimeException e) {
            log.warn("create product failed", e);
            throw e;
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        if (productId < 1){
            throw new InvalidInputException("Invalid product ID " + productId + ". ID should not be less than 1");
        }

        log.debug("Deletes product entity with productId {}", productId);
        repository.delete(repository.findByProductId(productId));
    }

    @Override
    public void editProduct(Long productId) {

    }

    @Override
    public ProductSummaryResponse getProductSummaryByID(Long productId) {
        return null;
    }

    @Override
    public void editProductQuantity(List<OrderedProductDTO> productList) {

    }
}
