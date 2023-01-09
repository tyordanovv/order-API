package bg.tyordanovv.core.delivery;

import java.time.LocalDate;
import java.time.LocalTime;

public record DeliverySummary(Long id, DeliveryStatusEnum status, LocalDate lastChange) {}
