package bg.tyordanovv.order.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetailsEntity {
    @Id
    @Column(name = "ordered_details_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "delivery_status")
    private boolean isDelivered;
    @Column(name = "unit_price")
    private double unitPrice;

//    private double discount; TODO: add discount
    public OrderDetailsEntity(
            OrderEntity order,
            Long productId,
            int quantity,
            boolean isDelivered,
            double unitPrice
    ){
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.isDelivered = isDelivered;
        this.unitPrice = unitPrice;
    }
}
