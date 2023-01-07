package bg.tyordanovv.order.persistence;

import jakarta.persistence.*;
import lombok.*;

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
    private boolean delivered;
    @Column(name = "unit_price")
    private double unitPrice;

//    private double discount; TODO: add discount
}
