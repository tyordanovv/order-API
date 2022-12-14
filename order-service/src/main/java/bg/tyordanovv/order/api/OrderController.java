package bg.tyordanovv.order.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface OrderController {

    @PostMapping(
            value = "/api/v1/order/create",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> createOrder(@RequestBody(required = false) OrderRequest body);

    @PostMapping(value = "/api/v1/order/cancel/{orderId}")
    Mono<Void> cancelOrder(@PathVariable Long orderId);
    @PostMapping(value ="/api/v1/order/return/{orderId}")
    Mono<Void> returnOrder(@PathVariable Long orderId);

    @GetMapping(value = "/api/v1/order/user{email}")
    Flux<OrderSummary> getUserOrders(@PathVariable String email);

    @GetMapping(value = "/api/v1/order/{orderId}")
    Mono<OrderSummary> getOrder(@PathVariable Long orderId);
}
