package bg.tyordanovv.product.persistence;

import bg.tyordanovv.core.product.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Version
    private int version;
    private String name;
    private String description;
    private ProductType type;
    private double price;
    private double weight;
    private int quantity;

    public ProductEntity(
            String name,
            String description,
            ProductType type,
            double price,
            double weight,
            int quantity
    ) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.weight = weight;
        this.quantity = quantity;
    }
//    private Long reviewId; TODO: add review service
}
