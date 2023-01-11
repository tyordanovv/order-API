package bg.tyordanovv.delivery.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.controller.delivery.DeliveryManagementController;
import bg.tyordanovv.core.delivery.DeliveryStatusEnum;
import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.delivery.persistence.DeliveryEntity;
import bg.tyordanovv.delivery.persistence.DeliveryRepository;
import bg.tyordanovv.exceptions.BadRequestException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.delivery.UpdateDeliveryRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static bg.tyordanovv.core.delivery.DeliveryStatusEnum.*;

@Slf4j
@RestController
@NoArgsConstructor
public class DeliveryService implements DeliveryController, DeliveryManagementController {
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
    @Transactional
    public void cancelDelivery(Long deliveryId) {
        DeliveryEntity delivery = repository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("Delivery with this id does not exist!"));

        if (delivery.getStatus().equals(PROCESSING) || delivery.getStatus().equals(NON_AVAILABLE)){
            delivery.setStatus(CANCELED);
            repository.save(delivery);
        } else if (delivery.getStatus().equals(CANCELED)){
            throw new RuntimeException("Delivery is already canceled");
        } else
            throw new RuntimeException("Delivery could not be canceled! Current status is " +
                    delivery.getStatus());

    }

    @Override
    public void returnProduct(ReturnProductRequest request) {
        DeliveryEntity delivery = repository.findById(request.deliveryId())
                .orElseThrow(() -> new NotFoundException("Delivery with this productId does not exist!"));

        LocalDate currentTime = LocalDate.now();
        LocalDate currentDateMinus2Weeks = currentTime.minusWeeks(2);

        if (delivery.getStatus().equals(DELIVERED)
                && delivery.getLastUpdate().isAfter(currentDateMinus2Weeks)
                && (delivery.getOrderedAmount() >= request.quantity())){
            sendReturnLabels(delivery, request.quantity());
        } else if (delivery.getStatus().equals(RETURN_LABELS_SEND)) {
            //TODO send Labels
        } else {
            throw new BadRequestException("This item could not be returned!");
        }
    }

    @Override
    public List<DeliveryDTO> getAllDeliverySummary(Long orderId) {
        log.info("get delivery status for order {}.", orderId);
        List<DeliveryEntity> deliveryEntities = repository
                .findAllByOrderId(orderId).orElseThrow(
                        () -> new NotFoundException("No deliveries were found with order id " + orderId)
                );
        log.info("Delivery entities fetched.");
        return deliveryEntities.stream()
                .map(d -> new DeliveryDTO(d.getId(), d.getStatus(), d.getLastUpdate()))
                .collect(Collectors.toList());
    }

    private void sendReturnLabels(DeliveryEntity delivery, int quantity) {
        if (delivery.getOrderedAmount() < quantity){
            throw new BadRequestException("ERROR could not return more products than ordered!");
        } else if (delivery.getOrderedAmount() == quantity) {
            delivery.setStatus(RETURN_LABELS_SEND);
            repository.save(delivery);
            //TODO send labels
        } else {
            splitDelivery(delivery, quantity, RETURN_LABELS_SEND);
            //TODO send labels
        }
    }

    @Transactional
    private void splitDelivery(DeliveryEntity delivery, int quantity, DeliveryStatusEnum newDeliveryStatus) {
        DeliveryEntity newDelivery = new DeliveryEntity(
                delivery.getAddress(),
                delivery.getOrderId(),
                delivery.getProductId(),
                delivery.getProductName(),
                delivery.getOrderedAmount() - quantity
        );
        newDelivery.setStatus(newDeliveryStatus);
        repository.save(newDelivery);

        delivery.setQuantity(-quantity);
        repository.save(delivery);
    }

    @Override
    public void updateDelivery(UpdateDeliveryRequest request) {
        DeliveryEntity delivery = repository.findById(request.deliveryId())
                .orElseThrow(() -> new NotFoundException("Delivery was not found with id" + request.deliveryId()));
        delivery.setStatus(request.statusEnum());
        repository.save(delivery);
    }
}
