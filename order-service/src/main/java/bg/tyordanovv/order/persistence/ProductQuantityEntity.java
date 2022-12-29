package bg.tyordanovv.order.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductQuantityEntity {
    @Id
    private Long id;
    private int quantity;
    private double weight;

    @OneToMany(mappedBy = "product")
    private Set<OrderedProductEntity> orderedProductEntities = new HashSet<>();

}
