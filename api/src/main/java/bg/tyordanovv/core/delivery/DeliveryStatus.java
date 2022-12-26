package bg.tyordanovv.core.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeliveryStatus {
    private enum Status{
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELED,
        RETURNED,
        NON_AVAILABLE
    }

    int n;
}
