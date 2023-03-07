package bg.tyordanovv.delivery;

import static org.junit.jupiter.api.Assertions.*;

import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.core.delivery.DeliveryStatusEnum;
import bg.tyordanovv.delivery.persistence.DeliveryEntity;
import bg.tyordanovv.delivery.service.DeliveryMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

class MapperTests {

    private DeliveryMapper mapper = Mappers.getMapper(DeliveryMapper.class);


    List<DeliveryDTO> dtoList = Collections.singletonList(
            new DeliveryDTO(1l, "Burgas", LocalDate.now(), DeliveryStatusEnum.PROCESSING));

    @Test
    void mapperTest(){
        assertNotNull(mapper);

        for (DeliveryDTO dto: dtoList){
            DeliveryEntity entity = mapper.apiToEntity(dto);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getStatus(), entity.getStatus());
            assertEquals(dto.getAddress(), entity.getAddress());
            assertEquals(dto.getLastUpdate(), entity.getLastUpdate());

            DeliveryDTO dto2 = mapper.entityToApi(entity);
            assertEquals(dto2.getId(), entity.getId());
            assertEquals(dto2.getStatus(), entity.getStatus());
            assertEquals(dto2.getAddress(), entity.getAddress());
            assertEquals(dto2.getLastUpdate(), entity.getLastUpdate());
        }
    }

    @Test
    void mapperListTest(){
        assertNotNull(mapper);

        List<DeliveryEntity> deliveryEntityList = mapper.apiListToEntityList(dtoList);
        assertEquals(dtoList.size(), deliveryEntityList.size());

        DeliveryEntity entity = deliveryEntityList.get(0);
        DeliveryDTO dto = dtoList.get(0);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getAddress(), entity.getAddress());
        assertEquals(dto.getLastUpdate(), entity.getLastUpdate());

        List<DeliveryDTO> dtoList2 = mapper.entityListToApiList(deliveryEntityList);
        assertEquals(dtoList2.size(), deliveryEntityList.size());

        DeliveryDTO dto2 = dtoList.get(0);

        assertEquals(dto2.getId(), entity.getId());
        assertEquals(dto2.getStatus(), entity.getStatus());
        assertEquals(dto2.getAddress(), entity.getAddress());
        assertEquals(dto2.getLastUpdate(), entity.getLastUpdate());

    }
}
