package bg.tyordanovv.order.api;

import bg.tyordanovv.clients.product.ProductQuantityRequest;

import java.util.List;

public record OrderSummary(
        Long id,
        String deliveryStatus,
        String firstName,
        String lastName,
        List<ProductQuantityRequest> items
) {}
