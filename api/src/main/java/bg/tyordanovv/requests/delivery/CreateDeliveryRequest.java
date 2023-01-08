package bg.tyordanovv.requests.delivery;

import bg.tyordanovv.address.DestinationAddress;
import bg.tyordanovv.requests.product.OrderedProductDTO;

import java.util.List;

public record CreateDeliveryRequest
        (Long orderId, List<OrderedProductDTO>products, double weight, String address)
{}
