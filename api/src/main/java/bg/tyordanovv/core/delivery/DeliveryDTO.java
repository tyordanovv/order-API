package bg.tyordanovv.core.delivery;

import bg.tyordanovv.core.delivery.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryDTO{
        private Long id;
        private String address;
        private LocalDate lastUpdate;
        private DeliveryStatusEnum status;

}
