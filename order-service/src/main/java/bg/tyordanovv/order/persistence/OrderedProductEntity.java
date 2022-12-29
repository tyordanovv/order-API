package bg.tyordanovv.order.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordered_products")
@NoArgsConstructor
@Getter
public class OrderedProductEntity {
    @Id
    @Column(name = "ordered_product_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductQuantityEntity product;
    @Column(name = "num_of_products")
    private int numOfProducts;
    private boolean delivered;

    public OrderedProductEntity(OrderEntity order, ProductQuantityEntity product, int numOfProducts){
        this.id = order.getId() + product.getId();
        this.order = order;
        this.product = product;
        this.delivered = false;
    }
}
