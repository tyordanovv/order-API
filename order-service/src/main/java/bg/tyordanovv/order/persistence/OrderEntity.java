package bg.tyordanovv.order.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
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
    private Long id;
    @Version
    private int version;
    private Long orderNumber;
    private LocalTime createdOn;
    private String firstName;
    private String lastName;
    private String email;
    @Column(length = 12)
    private String number;
    private double price;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetailsEntity> orderedProductEntities = new HashSet<>();

    public OrderEntity(
            String firstName,
            String lastName,
            String email,
            String number,
            Set<OrderDetailsEntity> orderedProductEntities
    ){
        this.createdOn = LocalTime.now();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.orderedProductEntities = orderedProductEntities;
    }
}
