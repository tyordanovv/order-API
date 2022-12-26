package bg.tyordanovv.responses.order;

import bg.tyordanovv.requests.product.ProductQuantityRequest;

import java.util.List;

public record OrderSummaryResponse(
        Long id,
        String deliveryStatus,
        String firstName,
        String lastName,
        List<ProductQuantityRequest> items
) {}
