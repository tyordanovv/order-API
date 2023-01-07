package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.order.OrderController;
import bg.tyordanovv.core.delivery.DeliverySummary;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.order.persistence.OrderEntity;
import bg.tyordanovv.order.persistence.OrderRepository;
import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.responses.order.OrderSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class OrderServiceImpl implements OrderController {

    @Autowired
    private OrderManagementIntegration integrationOrder;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder(OrderRequest body) {
        //crete delivery

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
        List<OrderSummaryResponse> orderSummaryResponses = new ArrayList<>();

        List<OrderEntity> orderEntities = orderRepository.findByEmail(email);
        orderEntities
                .forEach(e -> {
                    List<DeliverySummary> deliverySummary = integrationOrder.getAllDeliverySummary(e.getId());
                    orderSummaryResponses.add(
                            new OrderSummaryResponse(
                                    e.getId(),
                                    e.getOrderNumber(),
                                    e.getCreatedOn(),
                                    e.getPrice(),
                                    deliverySummary
                            )
                    );
                });
        return orderSummaryResponses;
    }

    @Override
    public OrderSummaryResponse getOrder(Long orderId) {
        List<DeliverySummary> deliverySummary = integrationOrder.getAllDeliverySummary(orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No order found with this ID!"));

        return new OrderSummaryResponse(
                orderEntity.getId(),
                orderEntity.getOrderNumber(),
                orderEntity.getCreatedOn(),
                orderEntity.getPrice(),
                deliverySummary
        );
    }
}
