package gdsc.sc.bsafe.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name= "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder
    public Organization(String name) {
        this.name = name;
    }

}
