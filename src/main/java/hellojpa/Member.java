package hellojpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity //JPA를 사용한다는 인식
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String email;
    private String phoneNumber;

    //기본 생성자 있어야 함
    //내부적으로 reflection 이런 것을 사용하기 때문에 기본 생성자 있어야 한다/
    public Member() {
    }

    public Member(String name, Long id) {
        this.name = name;
        this.id = id;
    }
}
