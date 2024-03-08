package main1;


import javax.persistence.*;
import java.util.Date;

@Entity //JPA를 사용 하는 것으로 인식 한다
public class Member {

    @Id //pk 값
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") //DB에 column name = name
    private String name;

    private Integer age; //DB에 숫자 타입 형성

    @Enumerated(EnumType.STRING) //DB에는 enumType X -> Enumerated 사용
    private RoleType roleType;

    //날짜 타입의 경우 Date, time, timeStamped / DB는 구분해서 사용 (Mapping)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob //큰 컨텐츠 사용
    private String description;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
