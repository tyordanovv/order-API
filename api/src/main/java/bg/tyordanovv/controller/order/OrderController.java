package bg.tyordanovv.controller.order;

import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import bg.tyordanovv.responses.order.OrderDTO;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

//@Tag(name = "Order", description = "REST API for full order management.")
public interface OrderController {

    @PostMapping(
            value = "/api/v1/order/create",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<Void> createOrder(@RequestBody OrderRequest body);

    @PostMapping(value = "/api/v1/order/cancel/{deliveryId}")
    Mono<Void> cancelOrder(@PathVariable Long deliveryId);
    @PostMapping(value ="/api/v1/order/return")
    Mono<Void> returnProduct(@RequestBody ReturnProductRequest request);

    @GetMapping(value = "/api/v1/order/user/{email}")
    Flux<OrderDTO> getUserOrders(@PathVariable String email);

//    @Operation(
//            summary = "${api.order.get-order.description}",
//            description = "${api.order.get-order.notes}"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
//            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
//            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
//            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
//    })
    /**
     * Sample usage: curl HOST:PORT/api/v1/order/1
     *
     * @param id of the order
     * @return
     */
    @GetMapping(value = "/api/v1/order/{orderId}")
    Mono<OrderDTO> getOrder(@PathVariable Long id);
}
