package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.controller.product.ProductQuantity;
import bg.tyordanovv.core.delivery.DeliveryDTO;
import bg.tyordanovv.core.email.EmailType;
import bg.tyordanovv.exceptions.CustomHttpError;
import bg.tyordanovv.requests.delivery.CreateDeliveryRequest;
import bg.tyordanovv.requests.product.OrderedProductDTO;
import bg.tyordanovv.requests.product.ReturnProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class OrderManagementIntegration implements DeliveryController, EmailController, ProductQuantity {

    private final String PRODUCT_SERVICE_URL;
    private final String DELIVERY_SERVICE_URL;
    private final String EMAIL_SERVICE_URL;
    private final ObjectMapper mapper;

    private final WebClient webClient;

    @Autowired
    public OrderManagementIntegration(
            ObjectMapper mapper,
            WebClient.Builder webClient,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort,
            @Value("${app.delivery-service.host}") String deliveryServiceHost,
            @Value("${app.delivery-service.port}") String deliveryServicePort,
            @Value("${app.email-service.host}") String emailServiceHost,
            @Value("${app.email-service.port}") String emailServicePort
            ){
        this.mapper = mapper;
        this.webClient = webClient.build();
        this.PRODUCT_SERVICE_URL = "http://" + productServiceHost + ":" + productServicePort + "/api/v1/product/";
        this.DELIVERY_SERVICE_URL = "http://" + deliveryServiceHost + ":" + deliveryServicePort + "/api/v1/delivery/";
        this.EMAIL_SERVICE_URL = "http://" + emailServiceHost + ":" + emailServicePort;
    }

    @Override
    public Mono<Void> createDelivery(CreateDeliveryRequest body) {
        String url = DELIVERY_SERVICE_URL;

        log.debug("Will call the createDelivery API on URL: {}", url);
        return webClient
                .post()
                .uri(url)
                .body(Mono.just(body), CreateDeliveryRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> cancelDelivery(Long deliveryId) {
        String url = DELIVERY_SERVICE_URL + "cancel/" + deliveryId;
        log.debug("Will call cancelDelivery API on URL: {}", url);

        return webClient
                .post()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Flux<DeliveryDTO> getAllDeliverySummary(Long orderId) {
        String url = DELIVERY_SERVICE_URL + "get-status/order/" + orderId;
        log.debug("Will call getDeliverySummary API on URL: {}", url);

        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(DeliveryDTO.class);
    }

    @Override
    public void editProductQuantity(List<OrderedProductDTO> productList) {
        //TODO edit product quantity method

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

    public Mono<Void> returnProduct(ReturnProductRequest body) {
        String url = DELIVERY_SERVICE_URL + "return";
        log.debug("Will call returnDelivery API on URL: {}", url);

        return webClient
                .post()
                .uri(url)
                .body(Mono.just(body), ReturnProductRequest.class)
                .retrieve()
                .bodyToMono(Void.class);    }
}
