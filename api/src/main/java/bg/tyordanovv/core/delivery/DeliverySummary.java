package bg.tyordanovv.core.delivery;

import java.time.LocalTime;

public record DeliverySummary(Long id, DeliveryStatusEnum status, LocalTime lastChange) {}
