package bg.tyordanovv.responses.delivery;

import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.requests.product.OrderedProductDTO;

public record DeliverySummary(
        DeliveryDTO deliveryStatus,
        OrderedProductDTO orderedProductDTO
) {
}
