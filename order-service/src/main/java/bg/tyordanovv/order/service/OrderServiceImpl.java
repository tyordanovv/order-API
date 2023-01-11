package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.order.OrderController;
import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.order.persistence.OrderDetailsEntity;
import bg.tyordanovv.order.persistence.OrderDetailsRepository;
import bg.tyordanovv.order.persistence.OrderEntity;
import bg.tyordanovv.order.persistence.OrderRepository;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.order.OrderRequest;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import bg.tyordanovv.responses.order.OrderSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class OrderServiceImpl implements OrderController {

    private final OrderManagementIntegration integrationOrder;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderServiceImpl(
            OrderManagementIntegration integrationOrder,
            OrderRepository orderRepository,
            OrderDetailsRepository orderDetailsRepository
    ){
        this.integrationOrder = integrationOrder;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }


    @Override
    public void createOrder(OrderRequest body) {
        try {
            log.debug("creates a new composite entity for order");

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
                    .forEach(e -> listOfOrderDetails.add(
                            new OrderDetailsEntity(order, e.id(), e.quantity(), 2)
                    ));

            order.setOrderDetails(listOfOrderDetails);

            orderDetailsRepository.saveAll(listOfOrderDetails);
            orderRepository.save(order);

            //TODO MQ -> delivery service
            integrationOrder.createDelivery(
                    new CreateDeliveryRequest(order.getId(), body.productList(), 10, body.address()));

            log.debug("create order entity: {}", order.getId());

        } catch (RuntimeException e) {
            log.warn("create order failed", e);
            throw e;
        }
    }

    @Override
    public void cancelOrder(Long deliveryId) {
        try {
            integrationOrder.cancelDelivery(deliveryId);
        } catch (RuntimeException e) {
            log.warn("ERROR canceling order with id {}", deliveryId, e);
            throw e;
        }
    }

    @Override
    public void returnProduct(ReturnProductRequest request) {
        try {
            integrationOrder.returnProduct(request);
        } catch (RuntimeException e) {
            log.warn("ERROR returning delivery with id {}", request.deliveryId(), e);
            throw e;
        }
    }

    @Override
    public List<OrderSummaryResponse> getUserOrders(String email) {
        List<OrderSummaryResponse> orderSummaryResponses = new ArrayList<>();

        List<OrderEntity> orderEntities = orderRepository.findByEmail(email);
        orderEntities
                .forEach(e -> {
                    List<DeliveryDTO> deliveryDTO = integrationOrder.getAllDeliverySummary(e.getId());
                    orderSummaryResponses.add(
                            new OrderSummaryResponse(
                                    e.getId(),
                                    e.getOrderNumber(),
                                    e.getCreatedOn(),
                                    e.getPrice(),
                                    deliveryDTO
                            )
                    );
                });
        return orderSummaryResponses;
    }

    @Override
    public OrderSummaryResponse getOrder(Long orderId) {
        List<DeliveryDTO> deliveryDTO = integrationOrder.getAllDeliverySummary(orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("No order found with this ID!"));

        return new OrderSummaryResponse(
                orderEntity.getId(),
                orderEntity.getOrderNumber(),
                orderEntity.getCreatedOn(),
                orderEntity.getPrice(),
                deliveryDTO
        );
    }
}
