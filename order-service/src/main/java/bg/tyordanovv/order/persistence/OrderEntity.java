package bg.tyordanovv.order.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Version
    private int version;
    private LocalTime createdOn;
    private String firstName;
    private String lastName;
    private String email;
    @Column(length = 12)
    private String number;
    @OneToMany(mappedBy = "order")
    private Set<OrderedProductEntity> orderedProductEntities = new HashSet<>();

    public OrderEntity(
            String firstName,
            String lastName,
            String email,
            String number,
            Set<OrderedProductEntity> orderedProductEntities
    ){
        this.createdOn = LocalTime.now();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
    }
}
