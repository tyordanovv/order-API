package bg.tyordanovv.order.service;

import bg.tyordanovv.clients.delivery.CreateDeliveryRequest;
import bg.tyordanovv.clients.email.EmailRequestCancelOrder;
import bg.tyordanovv.clients.email.EmailRequestDeliveryStatus;
import bg.tyordanovv.clients.email.EmailRequestInvoice;
import bg.tyordanovv.clients.product.ProductQuantityRequest;
import bg.tyordanovv.delivery.api.DeliveryController;
import bg.tyordanovv.email.api.EmailController;
import bg.tyordanovv.exceptions.CustomHttpError;
import bg.tyordanovv.exceptions.InvalidInputException;
import bg.tyordanovv.exceptions.NotFoundException;
import bg.tyordanovv.product.api.CreateProductRequest;
import bg.tyordanovv.product.api.ProductController;
import bg.tyordanovv.product.api.ProductSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

@Slf4j
@Component
@AllArgsConstructor
public class OrderManagementIntegration implements DeliveryController, ProductController, EmailController {

    private final static String PRODUCT_SERVICE_URL = "http://localhost:8083";
    private final static String DELIVERY_SERVICE_URL = "http://localhost:8080";

    private final WebClient webClient;
    private final ObjectMapper mapper;

//    public OrderManagementIntegration(
//            WebClient.Builder webClient,
//            ObjectMapper mapper
//    ){
//        this.webClient = webClient.build();
//        this.mapper = mapper;
//    }

    @Override
    public Mono<Void> createDelivery(CreateDeliveryRequest request) {
        return null;
    }

    @Override
    public Mono<Void> cancelDelivery(Long deliveryId) {
        return null;
    }

    @Override
    public Mono<String> getDeliveryStatus(Long deliveryId) {
        log.info("in OrderManagementIntegration getDeliveryStatus");

        String url = DELIVERY_SERVICE_URL + "/api/v1/delivery/get-status/" + deliveryId;
        log.info(url);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(WebClientResponseException.class,
                        this::handleException
                );
    }

    @Override
    public Mono<Void> editProductQuantity(List<ProductQuantityRequest> productList) {
        return null;
    }

    @Override
    public Flux<ProductSummary> getProductByCategory(String category) {
        return null;
    }

    @Override
    public Mono<ProductSummary> getProductByID(Long productId) {
        return null;
    }

    @Override
    public Mono<Void> createProduct(CreateProductRequest request) {
        return null;
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return null;
    }

    @Override
    public Mono<Void> sendInvoice(EmailRequestInvoice requestInvoice) {
        log.info("send invoice in OrderManagementIntegration");
        return null;
    }

    @Override
    public Mono<Void> sendDeliveryStatus(EmailRequestDeliveryStatus requestDeliveryStatus) {
        log.info("send status in OrderManagementIntegration");
        return null;
    }

    @Override
    public Mono<Void> sendCancelConfirmation(EmailRequestCancelOrder requestCancelOrder) {
        log.info("send cancel conf in OrderManagementIntegration");
        return null;
    }

    @Override
    public Mono<Void> sendReturnOrderConfirmation(EmailRequestCancelOrder returnOrderRequest) {
        log.info("send return confirm in OrderManagementIntegration");
        return null;
    }

    private Throwable handleException(Throwable ex) {

        if (!(ex instanceof WebClientResponseException)) {
            log.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
            return ex;
        }

        WebClientResponseException wcre = (WebClientResponseException)ex;

        switch (wcre.getStatusCode()) {

            case NOT_FOUND:
                return new NotFoundException(getErrorMessage(wcre));

            case UNPROCESSABLE_ENTITY :
                return new InvalidInputException(getErrorMessage(wcre));

            default:
                log.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
                log.warn("Error body: {}", wcre.getResponseBodyAsString());
                return ex;
        }
    }

    private String getErrorMessage(WebClientResponseException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), CustomHttpError.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }
}
