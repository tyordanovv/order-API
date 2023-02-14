package bg.tyordanovv.requests.delivery;

import bg.tyordanovv.core.delivery.DeliveryStatusEnum;

public record UpdateDeliveryStatusRequest(Long deliveryId, DeliveryStatusEnum statusEnum) {}
