package bg.tyordanovv.email.service;

import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.requests.email.EmailRequestCancelOrder;
import bg.tyordanovv.requests.email.EmailRequestDeliveryStatus;
import bg.tyordanovv.requests.email.EmailRequestInvoice;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailService implements EmailController {
    @Override
    public void sendInvoice(EmailRequestInvoice requestInvoice) {
        log.info("send invoice in email");
//        return null;
    }

    @Override
    public void sendDeliveryStatus(EmailRequestDeliveryStatus requestDeliveryStatus) {
        log.info("send status in email");
//        return null;
    }

    @Override
    public void sendCancelConfirmation(EmailRequestCancelOrder requestCancelOrder) {
        log.info("send cancel conf in email");
//        return null;
    }

    @Override
    public void sendReturnOrderConfirmation(EmailRequestCancelOrder returnOrderRequest) {
        log.info("send return confirm in email");
//        return null;
    }
}
