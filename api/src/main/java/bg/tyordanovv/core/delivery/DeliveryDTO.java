package bg.tyordanovv.core.delivery;

import java.time.LocalDate;

public record DeliveryDTO(Long id, DeliveryStatusEnum status, LocalDate lastChange) {}
