package hellojpa.연관관계;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Album extends Item{
    private String artist;
}