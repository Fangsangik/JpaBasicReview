package member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Address address;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipcode){
        address = new Address(city, street, zipcode);
    }

    public AddressEntity (Long id, Address address){
        this.id= id;
        this.address= address;
    }

}
