package bg.tyordanovv.controller.delivery;



import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryController {

    @PostMapping("api/v1/delivery/")
    Mono<Void> createDelivery(@RequestBody CreateDeliveryRequest request);

    @PostMapping("api/v1/delivery/cancel/{deliveryId}")
    Mono<Void> cancelDelivery(@PathVariable("deliveryId") Long deliveryId);

    @PostMapping("api/v1/delivery/return")
    Mono<Void> returnProduct(@RequestBody ReturnProductRequest request);

//    @GetMapping("api/v1/delivery/get-status/{deliveryId}")
//    DeliverySummary getDeliverySummary(@PathVariable Long deliveryId);

    @GetMapping("api/v1/delivery/get-status/order/{orderId}")
    Flux<DeliveryDTO> getAllDeliverySummary(@PathVariable Long orderId);
}
