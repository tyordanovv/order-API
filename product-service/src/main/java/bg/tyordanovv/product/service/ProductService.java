package bg.tyordanovv.product.service;

import bg.tyordanovv.address.ServiceAddress;
import bg.tyordanovv.controller.product.ProductController;
import bg.tyordanovv.core.product.ProductDTO;
import bg.tyordanovv.core.product.ProductType;
import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.product.persistence.ProductEntity;
import bg.tyordanovv.product.persistence.ProductMapper;
import bg.tyordanovv.product.persistence.ProductRepository;
import bg.tyordanovv.requests.product.CreateProductRequest;
import bg.tyordanovv.requests.product.OrderedProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Level;

@Slf4j
@RestController
public class ProductService implements ProductController {
    private final ProductRepository repository;
    private final ProductMapper mapper;
//    private final ServiceAddress serviceAddress;

    @Autowired
    public ProductService(
            ProductRepository repository,
            ProductMapper mapper
//            ServiceAddress serviceAddress
    ){
        this.repository = repository;
        this.mapper = mapper;
//        this.serviceAddress = serviceAddress;
    }

    @Override
    public Flux<ProductDTO> getProductByCategory(ProductType type) {
        log.info("product summary list returned");
        //TODO add check if enum exists
        return repository.findByType(type)
                .map(mapper::entityToApi)
//                .map(this::setServiceAddress)
                ;
    }

    @Override
    public Mono<ProductDTO> createProduct(CreateProductRequest request) {
        ProductEntity productEntity = mapper.apiToEntity(request);
        Mono<ProductDTO> productDTOMono = repository.save(productEntity)
                .log(log.getName(), Level.FINE)
                .onErrorMap(
                        DuplicateKeyException.class,
                        ex -> new InvalidInputException("Duplicate key, Product Id: " + request.description()))
                .map(mapper::entityToApi);

        log.info("Successfully added new product with name " + productEntity.getName());

        log.info("Successfully added new product with Id " + productEntity.getProductId());
        return productDTOMono;
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        if (productId < 1){
            throw new InvalidInputException("Invalid product ID " + productId + ". ID should not be less than 1");
        }

        log.debug("Deletes product entity with productId {}", productId);
        return repository.findByProductId(productId)
                .log(log.getName(), Level.FINE)
                .map(repository::delete)
                .flatMap(e -> e);
    }

    @Override
    public Mono<Void> editProduct(Long productId) {
        //TODO add logic
        return null;
    }

    @Override
    public Mono<ProductDTO> getProductDTOByID(Long productId) {
        if (productId < 0){
            throw new InvalidInputException("Invalid productId: " + productId);
        }
        return repository.findByProductId(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Product with ID " + productId + " was not found")))
                .log(log.getName(), Level.FINE)
                .map(mapper::entityToApi)
//                .map(this::setServiceAddress)
        ;
    }

    @Override
    public void editProductQuantity(List<OrderedProductDTO> productList) {
        //TODO add Mono<>
    }

//    private ProductDTO setServiceAddress(ProductDTO product) {
//        product.setServiceAddress(serviceAddress.getServiceAddress());
//        return product;
//    }
}
