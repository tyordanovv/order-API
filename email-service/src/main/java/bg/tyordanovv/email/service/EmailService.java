package bg.tyordanovv.email.service;

import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.core.email.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService implements EmailController {
    @Override
    public void send(EmailType type, String request) {
    }

    @Override
    public String testEmail() {
        return "Hello";
    }
}
