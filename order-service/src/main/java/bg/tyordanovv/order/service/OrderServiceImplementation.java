package bg.tyordanovv.order.service;

import bg.tyordanovv.delivery.persistence.DeliveryStatus;
import bg.tyordanovv.order.api.OrderController;
import bg.tyordanovv.order.api.OrderRequest;
import bg.tyordanovv.order.api.OrderSummary;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
public class OrderServiceImplementation implements OrderController {

    private final OrderManagementIntegration integration;

    @Override
    public Mono<Void> createOrder(OrderRequest body) {
        return null;
    }

    @Override
    public Mono<Void> cancelOrder(Long orderId) {
        return null;
    }

    @Override
    public Mono<Void> returnOrder(Long orderId) {
        return null;
    }

    @Override
    public Flux<OrderSummary> getUserOrders(String email) {
        return null;
    }

    @Override
    public Mono<OrderSummary> getOrder(Long orderId) {
        log.info("in OrderServiceImplementation");
        String stringMono = String.valueOf(integration.getDeliveryStatus(orderId));
        System.out.println(stringMono);
        log.info("out OrderServiceImplementation");
        return null;
    }
}
