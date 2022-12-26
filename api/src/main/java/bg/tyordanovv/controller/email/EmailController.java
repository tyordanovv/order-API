package bg.tyordanovv.controller.email;

import bg.tyordanovv.requests.email.EmailRequestCancelOrder;
import bg.tyordanovv.requests.email.EmailRequestDeliveryStatus;
import bg.tyordanovv.requests.email.EmailRequestInvoice;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailController {

    void sendInvoice(@RequestBody(required = false) EmailRequestInvoice requestInvoice);

    void sendDeliveryStatus(@RequestBody(required = false) EmailRequestDeliveryStatus requestDeliveryStatus);

    void sendCancelConfirmation(@RequestBody(required = false) EmailRequestCancelOrder requestCancelOrder);

    void sendReturnOrderConfirmation(@RequestBody(required = false) EmailRequestCancelOrder returnOrderRequest);
}
