package bg.tyordanovv.requests.delivery;

import bg.tyordanovv.address.DestinationAddress;

import java.util.List;

public record CreateDeliveryRequest
        (List<Integer>productIDs, double weight, DestinationAddress address)
{}
