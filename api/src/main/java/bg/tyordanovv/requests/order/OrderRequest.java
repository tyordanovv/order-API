package bg.tyordanovv.requests.order;

import bg.tyordanovv.requests.product.OrderedProductDTO;

import java.util.List;

public record OrderRequest(
        String firstName,
        String lastName,
        String email,
        String number,
        String address,
        List<OrderedProductDTO> productList
) {
}
