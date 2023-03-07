package bg.tyordanovv.order.service;

import bg.tyordanovv.controller.delivery.DeliveryController;
import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.controller.product.ProductQuantityController;
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
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static java.util.logging.Level.FINE;

@Slf4j
@Component
public class OrderManagementIntegration implements DeliveryController, ProductQuantityController {

    private final String PRODUCT_SERVICE_URL;
    private final String DELIVERY_SERVICE_URL;
    private final String EMAIL_SERVICE_URL;
    private final ObjectMapper mapper;

    private final WebClient webClient;

    @Autowired
    public OrderManagementIntegration(
            ObjectMapper mapper,
            WebClient.Builder webClient
            ){
        this.mapper = mapper;
        this.webClient = webClient.build();
//        this.PRODUCT_SERVICE_URL = "http://localhost:8083/api/v1/product/";
//        this.DELIVERY_SERVICE_URL = "http://localhost:8080/api/v1/delivery/";
//        this.EMAIL_SERVICE_URL = "http://localhost:8081/";
        this.PRODUCT_SERVICE_URL = "http://product-service/api/v1/product/";
        this.DELIVERY_SERVICE_URL = "http://delivery-service/api/v1/delivery/";
        this.EMAIL_SERVICE_URL = "http://email-service/";
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
    public Mono<Void> editProductQuantity(List<OrderedProductDTO> productList) {
        String url = PRODUCT_SERVICE_URL + "order/";
        log.debug("Will call editProductQuantity API on URL: {}", url);

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productList))//creates JSON from List<OrderedProductDTO>
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Health> getProductHealth() {
        return getHealth(PRODUCT_SERVICE_URL);
    }

    public Mono<Health> getDeliveryHealth() {
        return getHealth(DELIVERY_SERVICE_URL);
    }

    public Mono<Health> getEmailHealth() {
        return getHealth(EMAIL_SERVICE_URL);
    }


    private Mono<Health> getHealth(String url) {
        url += "/actuator/health";
        log.debug("Will call the Health API on URL: {}", url);
        return webClient.get().uri(url).retrieve().bodyToMono(String.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()))
                .log(log.getName(), FINE);
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), CustomHttpError.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }

//    @Override
//    public void send(EmailType type, String request) {
//
//    }

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
