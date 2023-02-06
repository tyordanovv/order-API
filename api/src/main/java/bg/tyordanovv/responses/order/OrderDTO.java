package bg.tyordanovv.responses.order;

import bg.tyordanovv.core.delivery.DeliveryDTO;

import java.time.LocalTime;
import java.util.List;

public record OrderDTO(
        Long id,
        Long oderNumber,
        LocalTime createdOn,
        double price,
        List<DeliveryDTO> deliveredProducts
) {}
