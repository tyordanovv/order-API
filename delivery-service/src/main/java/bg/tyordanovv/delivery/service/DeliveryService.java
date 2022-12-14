package bg.tyordanovv.delivery.service;

import bg.tyordanovv.clients.delivery.CreateDeliveryRequest;
import bg.tyordanovv.delivery.api.DeliveryController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class DeliveryService implements DeliveryController {
    @Override
    public Mono<Void> createDelivery(CreateDeliveryRequest request) {
        log.info("create delivery");
        return null;
    }

    @Override
    public Mono<Void> cancelDelivery(Long deliveryId) {
        log.info("cancel delivery");
        return null;
    }

    @Override
    public Mono<String> getDeliveryStatus(Long deliveryId) {
        log.info("get delivery " + deliveryId);

        return null;
    }
}
