package bg.tyordanovv.email.factory;

import bg.tyordanovv.core.email.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
public class ConfirmationSender implements Sender<ConfirmationSender> {
    private static final EmailType CONFIRMATION = EmailType.CONFIRMATION; // order or cancel
    @Override
    public void send(EmailType type, String request) {
        log.info("Send confirmation {}", request);
    }

    @Override
    public EmailType getType() {
        return CONFIRMATION;
    }
}
