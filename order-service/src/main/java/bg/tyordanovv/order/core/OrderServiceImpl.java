package bg.tyordanovv.order.core;

import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.order.persistence.OrderDetailsEntity;
import bg.tyordanovv.order.persistence.OrderDetailsRepository;
import bg.tyordanovv.order.persistence.OrderEntity;
import bg.tyordanovv.order.persistence.OrderRepository;
import bg.tyordanovv.requests.order.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.logging.Level.FINE;

@Service
@Slf4j
public class OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public OrderServiceImpl(
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler,
            OrderRepository orderRepository,
            OrderDetailsRepository orderDetailsRepository
    ) {
        this.jdbcScheduler = jdbcScheduler;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public void saveOrder(OrderRequest body) {
        OrderEntity order = new OrderEntity(
                1111111111L,
                body.firstName(),
                body.lastName(),
                body.email(),
                body.number()
        );

        //TODO: add check if product is available and get final price and weight

        Set<OrderDetailsEntity> listOfOrderDetails = new HashSet<>();
        body.productList()
                .forEach(e -> {
                    OrderDetailsEntity detailsEntity = new OrderDetailsEntity(order, e.id(), e.quantity(), 2.0);
                    orderDetailsRepository.save(detailsEntity);
                    listOfOrderDetails.add(detailsEntity);
                });

        order.setOrderDetails(listOfOrderDetails);
        orderRepository.save(order);
    }

    public Flux<OrderEntity> getOrdersByEmail(String email) {
        return Mono.fromCallable(() -> orderRepository.findByEmail(email))
                .flatMapMany(Flux::fromIterable)
                .log(log.getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }

    public Mono<OrderEntity> getOrderById(Long orderId) {
        if (orderId < 1) {
            throw new InvalidInputException("Invalid input for order ID: " + orderId);
        }

        log.info("Executing SELECT order for ID = {}", orderId);
        return Mono.fromCallable(() -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new NotFoundException("Order with ID " + orderId + " was not found")))
                .log(log.getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }
}
