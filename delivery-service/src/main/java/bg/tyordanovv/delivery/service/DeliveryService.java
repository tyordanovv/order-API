package bg.tyordanovv.delivery.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.core.delivery.DeliverySummary;
import bg.tyordanovv.core.product.ProductSummary;
import bg.tyordanovv.delivery.persistence.DeliveryEntity;
import bg.tyordanovv.delivery.persistence.DeliveryRepository;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@NoArgsConstructor
public class DeliveryService implements DeliveryController {
    @Autowired
    private DeliveryRepository repository;

    @Override
    public void createDelivery(CreateDeliveryRequest request) {
        log.info("create delivery");
        request.products().forEach(
                e -> repository.save(
                        new DeliveryEntity(request.address(), request.orderId(), e.id(), e.name(), e.quantity()))
        );
    }

    @Override
    public void cancelDelivery(Long deliveryId) {
        log.info("cancel delivery");
//        return null;
    }

//    @Override
//    public DeliverySummary getDeliverySummary(Long deliveryId) {
//        log.info("get delivery " + deliveryId);
////        DeliveryEntity delivery = repository.findByOrderId()
//        return null;
//    }


    @Override
    public List<DeliverySummary> getAllDeliverySummary(Long orderId) {
        log.info("get delivery status for order {}.", orderId);
        List<DeliveryEntity> deliveryEntities = repository
                .findAllByOrderId(orderId).orElseThrow(
                        () -> new NotFoundException("No deliveries were found with order id " + orderId)
                );
        log.info("Delivery entities fetched.");
        return deliveryEntities.stream()
                .map(d -> new DeliverySummary(d.getId(), d.getStatus(), d.getLastUpdate()))
                .collect(Collectors.toList());
    }
}
