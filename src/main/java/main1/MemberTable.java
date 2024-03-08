package main1;

import javax.persistence.*;

@Entity
@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnName = "MEMBER_SEQ", allocationSize = 1
)
public class MemberTable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

}
