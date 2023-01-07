package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.controller.product.ProductQuantity;
import bg.tyordanovv.core.delivery.DeliverySummary;
import bg.tyordanovv.core.email.EmailType;
import bg.tyordanovv.exceptions.CustomHttpError;
import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.order.persistence.OrderEntity;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.product.OrderedProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class OrderManagementIntegration implements DeliveryController, EmailController, ProductQuantity {

    private final String PRODUCT_SERVICE_URL;
    private final String DELIVERY_SERVICE_URL;
    private final String EMAIL_SERVICE_URL;

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public OrderManagementIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort,
            @Value("${app.delivery-service.host}") String deliveryServiceHost,
            @Value("${app.delivery-service.port}") String deliveryServicePort,
            @Value("${app.email-service.host}") String emailServiceHost,
            @Value("${app.email-service.port}") String emailServicePort
            ){
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.PRODUCT_SERVICE_URL = "http://" + productServiceHost + ":" + productServicePort + "/api/v1/product/";
        this.DELIVERY_SERVICE_URL = "http://" + deliveryServiceHost + ":" + deliveryServicePort + "/api/v1/delivery/";
        this.EMAIL_SERVICE_URL = "http://" + emailServiceHost + ":" + emailServicePort;
    }

    @Override
    public void createDelivery(CreateDeliveryRequest body) {
        try {
            log.debug("creates a new composite entity for order");

            OrderEntity order = new OrderEntity();

//            integration.createProduct(product);
//
//            if (body.getRecommendations() != null) {
//                body.getRecommendations().forEach(r -> {
//                    Recommendation recommendation = new Recommendation(body.getProductId(), r.getRecommendationId(), r.getAuthor(), r.getRate(), r.getContent(), null);
//                    integration.createRecommendation(recommendation);
//                });
//            }
//
//            if (body.getReviews() != null) {
//                body.getReviews().forEach(r -> {
//                    Review review = new Review(body.getProductId(), r.getReviewId(), r.getAuthor(), r.getSubject(), r.getContent(), null);
//                    integration.createReview(review);
//                });
//            }

            log.debug("create order entity: {}", order.getId());

        } catch (RuntimeException e) {
            log.warn("create order failed", e);
            throw e;
        }
    }

    @Override
    public void cancelDelivery(Long deliveryId) {
        log.info("order mng cancel delivery");

    }

    @Override
    public DeliverySummary getDeliverySummary(Long deliveryId) {
        DeliverySummary deliverySummary = null;
        try {
            String url = DELIVERY_SERVICE_URL + "get-status/" + deliveryId;
            log.debug("Will call getDeliverySummary API on URL: {}", url);

            deliverySummary = restTemplate.getForObject(url, DeliverySummary.class);
            log.debug("Found a product with id: {}", deliverySummary.getId());
        } catch (HttpClientErrorException e){
            HttpStatusCode statusCode = e.getStatusCode();

            if (statusCode.equals(NOT_FOUND)) {
                throw new NotFoundException(getErrorMessage(e));
            } else if (statusCode.equals(UNPROCESSABLE_ENTITY)) {
                throw new InvalidInputException(getErrorMessage(e));
            } else {
                log.warn("UNEXPECTED HTTP ERROR: {}, ERROR WILL BE RETHROWN", e.getStatusCode());
                log.warn("Error body: {}", e.getResponseBodyAsString());
            }
        }
        return deliverySummary;
    }

    @Override
    public List<DeliverySummary> getAllDeliverySummary(Long orderId) {
        List<DeliverySummary> deliverySummary = null;
        try {
            String url = DELIVERY_SERVICE_URL + "get-status/order/" + orderId;
            log.debug("Will call getDeliverySummary API on URL: {}", url);

            deliverySummary = restTemplate
                    .exchange(url, GET, null, new ParameterizedTypeReference<List<DeliverySummary>>() {})
                    .getBody();

        } catch (HttpClientErrorException e){
            HttpStatusCode statusCode = e.getStatusCode();

            if (statusCode.equals(NOT_FOUND)) {
                throw new NotFoundException(getErrorMessage(e));
            } else if (statusCode.equals(UNPROCESSABLE_ENTITY)) {
                throw new InvalidInputException(getErrorMessage(e));
            } else {
                log.warn("UNEXPECTED HTTP ERROR: {}, ERROR WILL BE RETHROWN", e.getStatusCode());
                log.warn("Error body: {}", e.getResponseBodyAsString());
            }
        }
        return deliverySummary;
    }

    @Override
    public void editProductQuantity(List<OrderedProductDTO> productList) {
        log.info("order mng editProductQuantity");
        //        return null;
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), CustomHttpError.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }

    @Override
    public void send(EmailType type, String request) {

    }
}
