package bg.tyordanovv.email.factory;

import bg.tyordanovv.core.email.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class ReturnLabelSender implements Sender<ReturnLabelSender> {
    private static final EmailType RETURN_LABEL = EmailType.RETURN_LABEL;

    @Override
    public void send(EmailType type, String request) {
        log.info("send return label {}", request);
    }

    @Override
    public String testEmail() {
        return "Hello 4";
    }
    @Override
    public EmailType getType() {
        return RETURN_LABEL;
    }
}
