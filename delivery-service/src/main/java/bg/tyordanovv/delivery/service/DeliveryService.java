package bg.tyordanovv.delivery.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.core.delivery.DeliverySummary;
import bg.tyordanovv.core.product.ProductSummary;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.responses.delivery.DeliverySummaryList;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@NoArgsConstructor
public class DeliveryService implements DeliveryController {
    @Override
    public void createDelivery(CreateDeliveryRequest request) {
        log.info("create delivery");
//        OrderEntity entity = new OrderEntity();
        ProductSummary product = new ProductSummary();
    }

    @Override
    public void cancelDelivery(Long deliveryId) {
        log.info("cancel delivery");
//        return null;
    }

    @Override
    public DeliverySummary getDeliverySummary(Long deliveryId) {
        log.info("get delivery " + deliveryId);
//        return Mono.just(new DeliveryStatus(1));
        return new DeliverySummary();
    }

    @Override
    public List<DeliverySummary> getAllDeliverySummary(Long orderId) {
        return null;
    }
}
