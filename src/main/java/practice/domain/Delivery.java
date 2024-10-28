package practice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String city;
    private String zipCode;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    private DeliveryStatus deliveryStatus;
}
