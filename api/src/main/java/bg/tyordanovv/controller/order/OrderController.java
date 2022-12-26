package bg.tyordanovv.controller.order;

import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.responses.order.OrderSummaryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface OrderController {

    @PostMapping(
            value = "/api/v1/order/create",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void createOrder(@RequestBody(required = false) OrderRequest body);

    @PostMapping(value = "/api/v1/order/cancel/{orderId}")
    void cancelOrder(@PathVariable Long orderId);
    @PostMapping(value ="/api/v1/order/return/{orderId}")
    void returnOrder(@PathVariable Long orderId);

    @GetMapping(value = "/api/v1/order/user{email}")
    List<OrderSummaryResponse> getUserOrders(@PathVariable String email);

    @GetMapping(value = "/api/v1/order/{orderId}")
    OrderSummaryResponse getOrder(@PathVariable Long orderId);
}
