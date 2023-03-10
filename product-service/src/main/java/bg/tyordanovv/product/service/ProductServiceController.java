package bg.tyordanovv.product.service;

import bg.tyordanovv.controller.product.ProductControllerController;
import bg.tyordanovv.core.product.ProductDTO;
import bg.tyordanovv.core.product.ProductType;
import bg.tyordanovv.exceptions.BadRequestException;
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
public class ProductServiceController implements ProductControllerController {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Autowired
    public ProductServiceController(
            ProductRepository repository,
            ProductMapper mapper
    ){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ProductDTO> getProductByCategory(ProductType type) {
        log.info("product summary list returned");
        if (type != null) {
            return repository.findByType(type)
                    .map(mapper::entityToApi)
                    ;
        } else {
            return Flux.error(() -> new InvalidInputException("Invalid product type input: NONE"));
        }

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
    public Mono<Void> editProduct(ProductDTO productDTO) {
        if (productDTO.getProductId() < 1){
            return Mono.error(
                    new InvalidInputException(
                            "Invalid product ID " + productDTO.getProductId() + ". ID should not be less than 1"
                    ));
        }
        log.debug("Edit product entity with productId {}", productDTO.getProductId());
        return repository.findByProductId(productDTO.getProductId())
                .flatMap(productEntity -> {
                    if (productDTO.getName() != null) productEntity.setName(productDTO.getName());
                    if (productDTO.getDescription() != null) productEntity.setDescription(productDTO.getDescription());
                    if (productDTO.getType() != null) productEntity.setType(productDTO.getType());
                    if (productDTO.getPrice() != null) productEntity.setPrice(productDTO.getPrice());
                    return repository.save(productEntity);
                })
                .then();
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
    public Mono<Void> editProductQuantity(List<OrderedProductDTO> productList) {
        return Flux.fromIterable(productList)
                .flatMap(orderedProductDTO -> repository.findByProductId(orderedProductDTO.id())
                        .flatMap(product -> {
                            int currentQuantity = product.getQuantity();
                            int orderedQuantity = orderedProductDTO.quantity();

                            if (currentQuantity < orderedQuantity) {
                                return Mono.error(new BadRequestException("Not enough product quantity!"));
                            } else {
                                product.setQuantity(currentQuantity - orderedQuantity);
                                return repository.save(product);
                            }
                        }))
                .then();
    }
}
