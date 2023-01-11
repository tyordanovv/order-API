package bg.tyordanovv.product.persistence;

import bg.tyordanovv.core.product.ProductDTO;
import bg.tyordanovv.requests.product.CreateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)
    })
    ProductDTO entityToApi(ProductEntity entity);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "productId", ignore = true)
    })
    ProductEntity apiToEntity(CreateProductRequest productDTO);
}
