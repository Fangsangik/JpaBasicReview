package hellojpa.연관관계;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MemberB {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    //@Column(name = "TEAM_ID")
    //private Long teamId; //직접 적어준 상황

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public MemberB() {

    }
}
