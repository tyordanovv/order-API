package bg.tyordanovv.product.persistence;

import bg.tyordanovv.core.product.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class ProductEntity {
    @Id
    private String id;
    @Version
    private int version;

    @Indexed(unique = true)
    private Long productId;
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
