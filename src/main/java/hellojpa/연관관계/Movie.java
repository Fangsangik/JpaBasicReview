package hellojpa.연관관계;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "M")
public class Movie extends Item{
    private String director;
    private String actor;
}
