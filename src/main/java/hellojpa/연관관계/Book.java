package hellojpa.연관관계;

import jakarta.persistence.Entity;

@Entity
public class Book extends Item{
    private String author;
    private String isbn;

}
