package bg.tyordanovv.requests.delivery;

import bg.tyordanovv.core.delivery.DeliveryStatusEnum;

public record UpdateDeliveryRequest(Long deliveryId, DeliveryStatusEnum statusEnum) {}
