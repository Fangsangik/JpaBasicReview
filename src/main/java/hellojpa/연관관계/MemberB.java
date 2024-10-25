package hellojpa.연관관계;

import hellojpa.mapping.MemberA;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MemberB extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    private int age;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany (mappedBy = "memberB")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public MemberB() {

    }
}
