package bg.tyordanovv.controller.delivery;



import bg.tyordanovv.core.delivery.DeliveryStatus;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import org.springframework.web.bind.annotation.*;

public interface DeliveryController {

    void createDelivery(@RequestBody(required = false) CreateDeliveryRequest request);

    @PostMapping("" +
            "cancel/{deliveryId}")
    void cancelDelivery(@PathVariable("deliveryId") Long deliveryId);

    @GetMapping("api/v1/delivery/get-status/{deliveryId}")
    DeliveryStatus getDeliveryStatus(@PathVariable Long deliveryId);
}
