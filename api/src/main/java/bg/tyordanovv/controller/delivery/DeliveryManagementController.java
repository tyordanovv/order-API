package bg.tyordanovv.controller.delivery;

import bg.tyordanovv.requests.delivery.UpdateDeliveryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeliveryManagementController {
    @PostMapping("api/v1/delivery/update")
    void updateDelivery(@RequestBody UpdateDeliveryRequest request);
}
