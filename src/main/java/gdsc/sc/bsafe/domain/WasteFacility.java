package gdsc.sc.bsafe.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "waste_facility")
public class WasteFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String sido;

    @Column(nullable = false)
    private String gu;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column
    private String tel;
}
