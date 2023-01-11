package bg.tyordanovv.controller.delivery;



import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DeliveryController {

    @PostMapping("api/v1/delivery/create")
    void createDelivery(@RequestBody CreateDeliveryRequest request);

    @PostMapping("api/v1/delivery/cancel/{deliveryId}")
    void cancelDelivery(@PathVariable("deliveryId") Long deliveryId);

    @PostMapping("api/v1/delivery/return")
    void returnProduct(@RequestBody ReturnProductRequest request);

//    @GetMapping("api/v1/delivery/get-status/{deliveryId}")
//    DeliverySummary getDeliverySummary(@PathVariable Long deliveryId);

    @GetMapping("api/v1/delivery/get-status/order/{orderId}")
    List<DeliveryDTO> getAllDeliverySummary(@PathVariable Long orderId);
}
