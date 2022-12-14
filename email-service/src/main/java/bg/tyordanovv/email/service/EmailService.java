package bg.tyordanovv.email.service;

import bg.tyordanovv.clients.email.EmailRequestCancelOrder;
import bg.tyordanovv.clients.email.EmailRequestDeliveryStatus;
import bg.tyordanovv.clients.email.EmailRequestInvoice;
import bg.tyordanovv.email.api.EmailController;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class EmailService implements EmailController {
    @Override
    public Mono<Void> sendInvoice(EmailRequestInvoice requestInvoice) {
        log.info("send invoice in email");
        return null;
    }

    @Override
    public Mono<Void> sendDeliveryStatus(EmailRequestDeliveryStatus requestDeliveryStatus) {
        log.info("send status in email");
        return null;
    }

    @Override
    public Mono<Void> sendCancelConfirmation(EmailRequestCancelOrder requestCancelOrder) {
        log.info("send cancel conf in email");
        return null;
    }

    @Override
    public Mono<Void> sendReturnOrderConfirmation(EmailRequestCancelOrder returnOrderRequest) {
        log.info("send return confirm in email");
        return null;
    }
}
