package bg.tyordanovv.controller.order;

import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.responses.order.OrderSummaryResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Tag(name = "Order", description = "REST API for full order management.")
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
    OrderSummaryResponse getOrder(@PathVariable Long id);
}
