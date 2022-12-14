package bg.tyordanovv.clients.delivery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "bg/tyordanovv/clients/delivery",
        url = "http://localhost:8080"
)
public interface DeliveryClient {
    @PostMapping("/api/v1/delivery/create")
    Mono<Void> createDelivery(CreateDeliveryRequest request);

    @PostMapping("/api/v1/delivery/cancel/{deliveryId}")
    Mono<Void> cancelDelivery(@PathVariable("deliveryId") Long deliveryId);

    @GetMapping("/api/v1/delivery/{deliveryId}")
    Mono<String> getDeliveryStatus(@PathVariable("deliveryId") Long deliveryId);
}
