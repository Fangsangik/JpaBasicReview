package practice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class Address {

    //주소
    @Column(length = 10)
    private String street;
    @Column(length = 20)
    private String city;
    @Column(length = 5)
    private String zipCode;

    public String fullAddress() {
        return getCity() + " " + getZipCode() +  " " + getStreet();
    }

    public Address() {
    }

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    //getter로 접근하지 않으면 proxy일때 계산 안됨
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, zipCode);
    }
}
