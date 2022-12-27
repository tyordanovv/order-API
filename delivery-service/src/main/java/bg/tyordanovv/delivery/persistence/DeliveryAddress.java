package bg.tyordanovv.delivery.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddress {
    @Id
    private Long id;
    @Version
    private int version;
    private String city;
    private String state;
    private String street;
    private int postNumber;
    @OneToMany(mappedBy = "address")
    private List<DeliveryEntity> delivery;
}
