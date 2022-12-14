package bg.tyordanovv.email.api;

import bg.tyordanovv.clients.email.EmailRequestCancelOrder;
import bg.tyordanovv.clients.email.EmailRequestDeliveryStatus;
import bg.tyordanovv.clients.email.EmailRequestInvoice;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface EmailController {

    Mono<Void> sendInvoice(@RequestBody(required = false) EmailRequestInvoice requestInvoice);

    Mono<Void> sendDeliveryStatus(@RequestBody(required = false) EmailRequestDeliveryStatus requestDeliveryStatus);

    Mono<Void> sendCancelConfirmation(@RequestBody(required = false) EmailRequestCancelOrder requestCancelOrder);

    Mono<Void> sendReturnOrderConfirmation(@RequestBody(required = false) EmailRequestCancelOrder returnOrderRequest);
}
