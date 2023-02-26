package bg.tyordanovv.delivery.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.controller.delivery.DeliveryManagementController;
import bg.tyordanovv.core.delivery.DeliveryStatusEnum;
import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.delivery.persistence.DeliveryEntity;
import bg.tyordanovv.delivery.persistence.DeliveryRepository;
import bg.tyordanovv.exceptions.BadRequestException;
import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static bg.tyordanovv.core.delivery.DeliveryStatusEnum.*;
import static java.util.logging.Level.FINE;

@Slf4j
@RestController
public class DeliveryService implements DeliveryManagementController {
    private final DeliveryRepository repository;
    private final DeliveryMapper mapper;
    private final Scheduler jdbcScheduler;
//    private final ServiceAddress serviceAddress;


    public DeliveryService(
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler,
            DeliveryRepository repository,
            DeliveryMapper mapper
//            ,ServiceAddress serviceAddress
    ) {
        this.jdbcScheduler = jdbcScheduler;
        this.repository = repository;
        this.mapper = mapper;
//        this.serviceAddress = serviceAddress;
    }
    @Override
    public Mono<Void> createDelivery(CreateDeliveryRequest request) {
        log.info("Calling createDelivery");
        if (request.orderId() < 1) {
            throw new InvalidInputException("Invalid orderId: " + request.orderId());
        }
        return Mono.fromRunnable(() -> internalCreateDelivery(request))
                .subscribeOn(jdbcScheduler).then();
    }

    private void internalCreateDelivery(CreateDeliveryRequest request) {
        log.info("Calling internalCreateReview");
        try {
            request.products().forEach(
                e -> repository.save(
                        new DeliveryEntity(request.address(), request.orderId(), e.id(), e.name(), e.quantity()))
            );
            log.info("done");

        } catch (DataIntegrityViolationException dive) {
            throw new InvalidInputException("Duplicate key, Order Id: " + request.orderId());
        }
    }

    @Override
    @Transactional
    public Mono<Void> cancelDelivery(Long deliveryId) {
        return Mono.fromSupplier(() -> repository.findById(deliveryId))
                .flatMap(optionalDelivery -> {
                    if (optionalDelivery.isPresent()) {
                        return internalCancelDelivery(optionalDelivery);
                    } else {
                        return Mono.error(new NotFoundException("Delivery with this id does not exist!"));
                    }
                })
                .log(log.getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }

    private Mono<Void> internalCancelDelivery(Optional<DeliveryEntity> optionalDelivery) {
        DeliveryEntity delivery = optionalDelivery.get();
        if (delivery.getStatus().equals(PROCESSING) || delivery.getStatus().equals(NON_AVAILABLE)) {
            delivery.setStatus(CANCELED);
            return Mono.fromSupplier(() -> repository.save(delivery)).then();
        } else if (delivery.getStatus().equals(CANCELED)) {
            return Mono.error(new InvalidInputException("Delivery is already canceled"));
        } else {
            return Mono.error(new InvalidInputException("Delivery could not be canceled! Current status is " + delivery.getStatus()));
        }
    }

    @Override
    public Mono<Void> returnProduct(ReturnProductRequest request) {
        return Mono.fromSupplier(() -> repository.findById(request.deliveryId()))
                .flatMap(optionalDelivery -> {
                    if (optionalDelivery.isPresent()) {
                        return internalReturnDelivery(optionalDelivery);
                    } else {
                        return Mono.error(new NotFoundException("Delivery with this id does not exist!"));
                    }
                })
                .log(log.getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }

    private Mono<Void> internalReturnDelivery(Optional<DeliveryEntity> optionalDelivery) {
        DeliveryEntity delivery = optionalDelivery.get();
        if (
                (
                        delivery.getStatus().equals(DELIVERED)
                                || delivery.getStatus().equals(RETURN_LABELS_SEND)
                )
                        && delivery.getLastUpdate().isAfter(LocalDate.now().minusWeeks(2))
        ) {
            delivery.setStatus(RETURN_LABELS_SEND);
            //TODO send Labels
            return Mono.fromSupplier(() -> repository.save(delivery)).then();
        } else if (delivery.getStatus().equals(RETURNED)) {
            return Mono.error(new InvalidInputException("Delivery is already returned!"));
        } else if ((
                delivery.getStatus().equals(DELIVERED)
                        || delivery.getStatus().equals(RETURN_LABELS_SEND)
        )
                && delivery.getLastUpdate().isBefore(LocalDate.now().minusWeeks(2))) {
            return Mono.error(new InvalidInputException("Delivery could not be returned! Delivered at " + delivery.getLastUpdate()));
        } else {
            return Mono.error(new InvalidInputException("Product is still not delivered! Delivery status " + delivery.getStatus()));
        }
    }

    @Override
    public Flux<DeliveryDTO> getAllDeliverySummary(Long orderId) {
        log.info("get delivery status for order {}.", orderId);
        return Mono.fromCallable(() -> repository.findAllByOrderId(orderId))
                .subscribeOn(jdbcScheduler)
                .flatMapMany(deliveryEntities -> {
                    List<DeliveryEntity> list = deliveryEntities.orElseThrow(() ->
                            new NotFoundException("No deliveries were found with order id " + orderId));
                    log.info("Delivery entities fetched.");
                    return Flux.fromIterable(list)
                            .map(d -> new DeliveryDTO(
                                    d.getId(),
                                    d.getAddress(),
                                    d.getLastUpdate(),
                                    d.getStatus()
//                                    ,serviceAddress.getServiceAddress()
                            ));
                });
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
    public Mono<Void> updateDelivery(DeliveryDTO request) {
        return Mono.fromSupplier(() -> repository.findById(request.getId()))
                .flatMap(optionalDelivery -> {
                    if (optionalDelivery.isPresent()) {
                        return internalUpdateDelivery(optionalDelivery, request);
                    } else {
                        return Mono.error(new NotFoundException("Delivery with this id does not exist!"));
                    }
                })
                .subscribeOn(jdbcScheduler)
                .then();
    }

    private Mono<Void> internalUpdateDelivery(Optional<DeliveryEntity> optionalDelivery, DeliveryDTO deliveryRequest) {
        DeliveryEntity delivery = optionalDelivery.get();
        if (deliveryRequest.getAddress() != null){
            delivery.setAddress(deliveryRequest.getAddress());
        }
        if (deliveryRequest.getStatus() != null){
            delivery.setStatus(deliveryRequest.getStatus());
        }
        return Mono.fromSupplier(() -> repository.save(delivery)).then();
    }
}
