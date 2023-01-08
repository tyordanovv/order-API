package bg.tyordanovv.controller.delivery;



import bg.tyordanovv.core.delivery.DeliverySummary;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.responses.delivery.DeliverySummaryList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DeliveryController {

    @PostMapping("api/v1/delivery/create")
    void createDelivery(@RequestBody CreateDeliveryRequest request);

    @PostMapping("api/v1/delivery/cancel/{deliveryId}")
    void cancelDelivery(@PathVariable("deliveryId") Long deliveryId);

//    @GetMapping("api/v1/delivery/get-status/{deliveryId}")
//    DeliverySummary getDeliverySummary(@PathVariable Long deliveryId);

    @GetMapping("api/v1/delivery/get-status/order/{orderId}")
    List<DeliverySummary> getAllDeliverySummary(@PathVariable Long orderId);
}
