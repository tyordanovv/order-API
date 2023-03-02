package bg.tyordanovv.controller.delivery;

import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.requests.delivery.UpdateDeliveryStatusRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface DeliveryManagementController extends DeliveryController {
    @PostMapping("api/v1/delivery-internal/update")
    Mono<Void> updateDelivery(@RequestBody DeliveryDTO request);
}
