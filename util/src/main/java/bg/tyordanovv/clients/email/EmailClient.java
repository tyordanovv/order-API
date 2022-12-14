package bg.tyordanovv.clients.email;

import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "email",
        url = "http://localhost:8081"
)
public interface EmailClient {

    @PostMapping("/api/v1/email/send-invoice")
    Mono<Void> sendInvoice(EmailRequestInvoice requestInvoice);

    @PostMapping("/api/v1/email/send-status")
    Mono<Void> sendDeliveryStatus(EmailRequestDeliveryStatus requestDeliveryStatus);

    @PostMapping("/api/v1/email/send-cancel-confirmation")
    Mono<Void> sendCancelConfirmation(EmailRequestCancelOrder requestCancelOrder);

    @PostMapping("/api/v1/email/send-return-confirmation")
    Mono<Void> sendReturnOrderConfirmation(EmailRequestCancelOrder returnOrderRequest);
}
