package hellojpa.mapping;

import hellojpa.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class MemberA {

    @Id
    private Long id;

    @Column(name = "name") //DB 컬럼명
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) //ENUM Type
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //날짜 Type
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob //DB에 큰 컨텐츠를 넣고 싶다면 (큰 컨텐츠를 넣고 싶다면)
    private String description;

    public MemberA(){

    }
}
