package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.order.OrderController;
import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.order.core.OrderServiceImpl;
import bg.tyordanovv.order.persistence.OrderEntity;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import bg.tyordanovv.responses.order.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static java.util.logging.Level.FINE;

@Slf4j
@RestController
@AllArgsConstructor
public class OrderManagementServiceImpl implements OrderController {

    private final OrderManagementIntegration integrationOrder;
    private final OrderServiceImpl orderService;


    @Override
    public Mono<Void> createOrder(OrderRequest body) {
        try {
            log.debug("creates a new composite entity for order");

            //TODO add check if products are available

            orderService.saveOrder(body);

            return Mono.zip(
                            r -> "",
                            integrationOrder.createDelivery(
                                    new CreateDeliveryRequest(
                                            1L,
                                            body.productList(),
                                            10,
                                            body.address()
                                    )))
                    .doOnError(ex -> log.warn("create order failed: {}", ex.toString()))
                    .log(log.getName(), FINE).then();


        } catch (RuntimeException e) {
            log.warn("create order failed", e);
            throw e;
        }
    }

    @Override
    public Mono<Void> cancelOrder(Long orderId) {
        try {
            return integrationOrder.cancelDelivery(orderId)
                    .doOnError(ex -> log.warn("create order failed: {}", ex.toString()))
                    .log(log.getName(), FINE).then();
        } catch (RuntimeException e) {
            log.warn("ERROR canceling order with id {}", orderId, e);
            throw e;
        }
    }

    @Override
    public Mono<Void> returnProduct(ReturnProductRequest request) {

        try {
            return integrationOrder.returnProduct(request)
                    .doOnError(ex -> log.warn("return product failed: {}", ex.toString()))
                    .then();
        } catch (RuntimeException e) {
            log.warn("ERROR returning delivery with id {}", request.deliveryId(), e);
            throw e;
        }
    }

    @Override
    public Flux<OrderDTO> getUserOrders(String email) {
        log.info("Will get user orders for user email: {}", email);

        return orderService.getOrdersByEmail(email)
                .flatMap(order -> integrationOrder.getAllDeliverySummary(order.getId())
                    .collectList()
                    .map(deliveryDTOS -> createOrderDTO(order, deliveryDTOS))
                );
    }

    @Override
    public Mono<OrderDTO> getOrder(Long orderId) {
        log.info("Will get user order for order ID: {}", orderId);

        return Mono.zip(
                values -> createOrderDTO((OrderEntity) values[0], (List<DeliveryDTO>) values[1]),
                        orderService.getOrderById(orderId),
                        integrationOrder.getAllDeliverySummary(orderId).collectList())
                .doOnError(e -> log.warn("getOrder in OrderManagementServiceImplementation failed: {}", e.toString()))
                .log(log.getName(), FINE);
    }

    private OrderDTO createOrderDTO(OrderEntity order, List<DeliveryDTO> deliveryDTOs) {
        return new OrderDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getCreatedOn(),
                order.getPrice(),
                deliveryDTOs
        );
    }
}
