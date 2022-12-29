package bg.tyordanovv.email.factory;

import bg.tyordanovv.controller.email.EmailController;
import bg.tyordanovv.core.email.EmailType;

public interface Sender<T> extends EmailController {
    EmailType getType();
}
