package bg.tyordanovv.core.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO{
        Long productId;
        String name;
        String description;
        ProductType type;
        double price;
        String serviceAddress;
}
