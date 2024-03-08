package member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany
//    @JoinTable(name = "TEAM_ID")
    @JoinColumn(insertable = false, updatable = false)
    List<Member1> members = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
}


