package gdsc.sc.bsafe.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long districtId;

    @Column(nullable = false)
    private String sido;

    @Column
    private String gu;

    @Column
    private String dong;

    @Column
    private String code;

    @Builder
    public District(String sido, String gu, String dong) {
        this.sido = sido;
        this.gu = gu;
        this.dong = dong;
    }

}