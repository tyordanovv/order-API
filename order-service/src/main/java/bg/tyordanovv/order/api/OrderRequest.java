package bg.tyordanovv.order.api;

import bg.tyordanovv.address.DestinationAddress;
import bg.tyordanovv.clients.product.ProductQuantityRequest;

import java.util.List;

public record OrderRequest(
        String firstName,
        String lastName,
        String email,
        String number,
        List<ProductQuantityRequest> productList,
        DestinationAddress address
) {
}
