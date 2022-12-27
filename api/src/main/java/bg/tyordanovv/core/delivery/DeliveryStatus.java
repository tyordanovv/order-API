package bg.tyordanovv.core.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeliveryStatus {
    private DeliveryStatusEnum status;
    private Date lastChange;
}
