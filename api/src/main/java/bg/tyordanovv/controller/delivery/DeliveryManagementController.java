package bg.tyordanovv.controller.delivery;

import bg.tyordanovv.requests.delivery.UpdateDeliveryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface DeliveryManagementController {
    @PostMapping("api/v1/delivery/update")
    Mono<Void> updateDelivery(@RequestBody UpdateDeliveryRequest request);
}
