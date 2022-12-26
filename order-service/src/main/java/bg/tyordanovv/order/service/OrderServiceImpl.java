package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.order.OrderController;
import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.requests.product.ProductQuantityRequest;
import bg.tyordanovv.responses.order.OrderSummaryResponse;
import bg.tyordanovv.responses.product.ProductSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OrderServiceImpl implements OrderController {

    @Autowired
    private OrderManagementIntegration integrationOrder;

    @Override
    public void createOrder(OrderRequest body) {
//        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {
//        return null;
    }

    @Override
    public void returnOrder(Long orderId) {
//        return null;
    }

    @Override
    public List<OrderSummaryResponse> getUserOrders(String email) {
        return null;
    }

    @Override
    public OrderSummaryResponse getOrder(Long orderId) {
//        Mono<DeliveryStatus> status = integration.getDeliveryStatus(orderId);
        ProductSummaryResponse product = integrationOrder.getProductByID(orderId);
        System.out.println(product);
        log.info("out OrderServiceImplementation");
        return new OrderSummaryResponse(
                5L,
                "done",
                "Peter",
                "John",
                List.of(new ProductQuantityRequest(product.id(), product.name(), product.orderedQuantity())));
    }
}
