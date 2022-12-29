package bg.tyordanovv.email.factory;

import bg.tyordanovv.core.email.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class DeliveryStatusSender implements Sender<DeliveryStatusSender>{
    private static final EmailType DELIVERY_STATUS = EmailType.DELIVERY_STATUS;
    @Override
    public void send(EmailType type, String request) {
        log.info("send delivery status {}", request);
    }

    @Override
    public EmailType getType() {
        return DELIVERY_STATUS;
    }
}
