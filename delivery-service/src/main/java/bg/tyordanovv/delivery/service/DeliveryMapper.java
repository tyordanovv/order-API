package bg.tyordanovv.delivery.service;

import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.delivery.persistence.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

//    //TODO fix issue with serviceAddress and implement mapper
//    @Mappings({
//            @Mapping(target = "serviceAddress", ignore = true)
//    })
    DeliveryDTO entityToApi(DeliveryEntity entity);

    @Mappings({
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "orderId", ignore = true),
            @Mapping(target = "productId", ignore = true),
            @Mapping(target = "productName", ignore = true),
            @Mapping(target = "orderedAmount", ignore = true),

    })
    DeliveryEntity apiToEntity(DeliveryDTO api);

    List<DeliveryDTO> entityListToApiList(List<DeliveryEntity> entity);

    List<DeliveryEntity> apiListToEntityList(List<DeliveryDTO> api);
}
