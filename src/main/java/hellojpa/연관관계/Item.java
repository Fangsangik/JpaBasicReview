package hellojpa.연관관계;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
}
