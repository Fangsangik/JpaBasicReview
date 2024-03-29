package member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Member1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    //기간
    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @AttributeOverrides(
            {@AttributeOverride(name = "city"
                    , column = @Column(name = "WORK_CITY")),
                    @AttributeOverride(name = "street"
                            , column = @Column(name = "WORK_STREET")),
                    @AttributeOverride(name = "zipcode"
                            , column = @Column(name = "WORK_ZIPCODE"))})
    private Address workAddress;

    //Member 입장 -> Many, Team 입장 -> 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID") //join 하는 column
    private Team team; //JPA에 관계를 알려주어야 한다

    @Column(name = "USER_NAME")
    private String userName;
    private int age;

    @OneToOne
    private Locker locker;

    @ManyToMany(mappedBy = "member1List")
    private List<Product> products = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD",
    joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FoodName")
    private Set<String> favoriteFood = new HashSet<>();

    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @CollectionTable(name = "ADDRESS"
    , joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> historyAddress = new ArrayList<>();
}

/*
pk - MemberId
fk - TeamId

외래 key가 있다는 의미는 Member_Id insert 할 때, Team_Id를 넣으면서
여러개 Member가 어떤 팀에 속해 있는지

여러면 회원에 하나 Team 소속
하나의 Team에 여러명 소속
 */