package practice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "delivery")
    private Order order;

    private DeliveryStatus deliveryStatus;
}
