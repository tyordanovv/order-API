package bg.tyordanovv.email.factory;

import bg.tyordanovv.core.email.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
public class InvoiceSender implements Sender<InvoiceSender> {
    private static final EmailType INVOICE = EmailType.INVOICE;

    @Override
    @GetMapping("/api/v1/email/invoice")
    public void send(EmailType type, String request) {
        log.info("send invoice {}", request);
    }

    @Override
    public EmailType getType() {
        return INVOICE;
    }
}
