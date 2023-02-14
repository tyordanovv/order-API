package bg.tyordanovv.core.delivery;

import bg.tyordanovv.core.delivery.DeliveryStatusEnum;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record DeliveryDTO(
        Long id,
        String address,
        LocalDate lastUpdate,
        DeliveryStatusEnum status
//        ,String serviceAddress
        ) {}
