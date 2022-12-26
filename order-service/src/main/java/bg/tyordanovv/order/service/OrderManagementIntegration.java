package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.controller.product.ProductController;
import bg.tyordanovv.core.delivery.DeliveryStatus;
import bg.tyordanovv.core.product.ProductEntity;
import bg.tyordanovv.exceptions.CustomHttpError;
import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.email.EmailRequestCancelOrder;
import bg.tyordanovv.requests.email.EmailRequestDeliveryStatus;
import bg.tyordanovv.requests.email.EmailRequestInvoice;
import bg.tyordanovv.requests.product.CreateProductRequest;
import bg.tyordanovv.requests.product.ProductQuantityRequest;
import bg.tyordanovv.responses.product.ProductSummaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class OrderManagementIntegration implements DeliveryController, ProductController, EmailController {

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
            @Value("${app.product-service.host}") String productServicePort,
            @Value("${app.delivery-service.host}") String deliveryServiceHost,
            @Value("${app.delivery-service.host}") String deliveryServicePort,
            @Value("${app.email-service.host}") String emailServiceHost,
            @Value("${app.email-service.host}") String emailServicePort
            ){
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.PRODUCT_SERVICE_URL = "http://" + productServiceHost + ":" + productServicePort + "/api/v1/product/";
        this.DELIVERY_SERVICE_URL = "http://" + deliveryServiceHost + ":" + deliveryServicePort + "/api/v1/delivery/";
        this.EMAIL_SERVICE_URL = "http://" + emailServiceHost + ":" + emailServicePort;
    }

    @Override
    public void createDelivery(CreateDeliveryRequest request) {
        log.info("order mng create delivery");
//        return null;
    }

    @Override
    public void cancelDelivery(Long deliveryId) {
        log.info("order mng cancel delivery");
//        return null;
    }

    @Override
    public DeliveryStatus getDeliveryStatus(Long deliveryId) {
        log.info("in OrderManagementIntegration getDeliveryStatus");

        String url = DELIVERY_SERVICE_URL + "/api/v1/delivery/get-status/" + deliveryId;
        log.info(url);

//        return webClient.get()
//                .uri(url)
//                .retrieve()
//                .bodyToMono(DeliveryStatus.class)
//                .log(log.getName(), Level.FINE)
//                .onErrorMap(WebClientResponseException.class,
//                        this::handleException
//                );
        return null;
    }

    @Override
    public void editProductQuantity(List<ProductQuantityRequest> productList) {
        log.info("order mng editProductQuantity");
        //        return null;
    }

    @Override
    public List<ProductSummaryResponse> getProductByCategory(String category) {
        log.info("order mng getProductByCategory");
        return null;
    }

    @Override
    public ProductSummaryResponse getProductByID(Long productId) {
        try {
            String url = PRODUCT_SERVICE_URL + productId;
            log.debug("Will call getProductByID API on URL: {}", url);

            ProductEntity product = restTemplate.getForObject(url, ProductEntity.class);
            log.debug("Found a product with id: {}", product.getProductId());
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
        return null;
    }

    @Override
    public void createProduct(CreateProductRequest request) {
        log.info("order mng createProduct");
//        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        log.info("order mng deleteProduct");
//        return null;
    }

    @Override
    public void sendInvoice(EmailRequestInvoice requestInvoice) {
        log.info("send invoice in OrderManagementIntegration");
//        return null;
    }

    @Override
    public void sendDeliveryStatus(EmailRequestDeliveryStatus requestDeliveryStatus) {
        log.info("send status in OrderManagementIntegration");
//        return null;
    }

    @Override
    public void sendCancelConfirmation(EmailRequestCancelOrder requestCancelOrder) {
        log.info("send cancel conf in OrderManagementIntegration");
//        return null;
    }

    @Override
    public void sendReturnOrderConfirmation(EmailRequestCancelOrder returnOrderRequest) {
        log.info("send return confirm in OrderManagementIntegration");
//        return null;
    }

//    private Throwable handleException(Throwable ex) {
//
//        if (!(ex instanceof WebClientResponseException)) {
//            log.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
//            return ex;
//        }
//
//        WebClientResponseException wcre = (WebClientResponseException)ex;
//
//        switch (wcre.getStatusCode()) {
//            case NOT_FOUND -> {
//                return new NotFoundException(getErrorMessage(wcre));
//            }
//            case UNPROCESSABLE_ENTITY -> {
//                return new InvalidInputException(getErrorMessage(wcre));
//            }
//            default -> {
//                log.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
//                log.warn("Error body: {}", wcre.getResponseBodyAsString());
//                return ex;
//            }
//        }
//    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), CustomHttpError.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }
}
