package bg.tyordanovv.delivery.persistence;

import bg.tyordanovv.address.DestinationAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "deliveries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private int version;
    @ManyToOne(targetEntity = DeliveryAddress.class)
    private DestinationAddress address;
    private Date lastUpdate;
    private Long orderId;
}
