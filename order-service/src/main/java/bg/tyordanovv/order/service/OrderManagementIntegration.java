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
import bg.tyordanovv.requests.product.ReturnProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpMethod.*;

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
            String url = DELIVERY_SERVICE_URL + "create";
            log.debug("Will call createDelivery API on URL: {}", url);

            ResponseEntity<CreateDeliveryRequest> response = restTemplate
                    .postForEntity(url, body, CreateDeliveryRequest.class);

            log.debug("Created delivery with status {}", response.getStatusCode());
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
    }

    @Override
    public void cancelDelivery(Long deliveryId) {
        try {
            String url = DELIVERY_SERVICE_URL + "cancel/" + deliveryId;
            log.debug("Will call cancelDelivery API on URL: {}", url);

            restTemplate.exchange(url, POST, null, Void.class);

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

    public void returnProduct(ReturnProductRequest product) {
        try {
            String url = DELIVERY_SERVICE_URL + "return";
            log.debug("Will call returnDelivery API on URL: {}", url);

            ResponseEntity<ReturnProductRequest> response = restTemplate
                    .postForEntity(url, product, ReturnProductRequest.class);

            log.debug("Returned delivery with status {}", response.getStatusCode());

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
    }
}
