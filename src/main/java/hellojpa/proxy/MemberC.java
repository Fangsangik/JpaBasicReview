package hellojpa.proxy;

import hellojpa.연관관계.BaseEntity;
import hellojpa.연관관계.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MemberC extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    public MemberC() {

    }
}

