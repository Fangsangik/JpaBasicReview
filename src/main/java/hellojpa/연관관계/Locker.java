package hellojpa.연관관계;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Locker {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

}