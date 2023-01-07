package bg.tyordanovv.responses.delivery;

import bg.tyordanovv.requests.product.OrderedProductDTO;

public record DeliverySummary(
        bg.tyordanovv.core.delivery.DeliverySummary deliveryStatus,
        OrderedProductDTO orderedProductDTO
) {
}
