package bg.tyordanovv.responses.order;

import bg.tyordanovv.core.delivery.DeliverySummary;

import java.time.LocalTime;
import java.util.List;

public record OrderSummaryResponse(
        Long id,
        Long oderNumber,
        LocalTime createdOn,
        double price,
        List<DeliverySummary> deliveredProducts
) {}
