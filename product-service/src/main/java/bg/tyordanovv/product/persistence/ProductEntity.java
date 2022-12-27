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
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Version
    private int version;
    private String name;
    private ProductType type;
    private double weight;
    private int quantity;
    private double review;
}
