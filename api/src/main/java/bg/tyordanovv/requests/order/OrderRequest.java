package bg.tyordanovv.requests.order;

import bg.tyordanovv.address.DestinationAddress;
import bg.tyordanovv.requests.product.ProductQuantityRequest;

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
