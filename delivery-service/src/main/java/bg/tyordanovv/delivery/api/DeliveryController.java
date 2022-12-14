package bg.tyordanovv.delivery.api;


import bg.tyordanovv.clients.delivery.CreateDeliveryRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

public interface DeliveryController {

    Mono<Void> createDelivery(@RequestBody(required = false) CreateDeliveryRequest request);

    @PostMapping("api/v1/delivery/cancel/{deliveryId}")
    Mono<Void> cancelDelivery(@PathVariable("deliveryId") Long deliveryId);

    @GetMapping("api/v1/delivery/get-status/{deliveryId}")
    Mono<String> getDeliveryStatus(@PathVariable Long deliveryId);
}
