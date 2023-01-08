package bg.tyordanovv.delivery.persistence;

import bg.tyordanovv.address.DestinationAddress;
import bg.tyordanovv.core.delivery.DeliveryStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "deliveries")
@NoArgsConstructor
@Getter
@Setter
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private int version;
//    @ManyToOne(targetEntity = DeliveryAddress.class)
    @Column(name = "address")
    private String address;
    @Column(name = "last_update")
    private LocalTime lastUpdate;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "ordered_amount")
    private int orderedAmount;
    @Column(name = "status")
    private DeliveryStatusEnum status;

    public DeliveryEntity(
            String address,
            Long orderId,
            Long productId,
            String productName,
            int orderedAmount
    ){
        this.address = address;
        this.lastUpdate = LocalTime.now();
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.orderedAmount = orderedAmount;
        this.status = DeliveryStatusEnum.PROCESSING;
    }

}
