package bg.tyordanovv.controller.email;

import bg.tyordanovv.core.email.EmailType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailController {
    void send(@PathVariable EmailType type, @RequestBody String request);

    @GetMapping("api/v1/email/test")
    String testEmail();
}
