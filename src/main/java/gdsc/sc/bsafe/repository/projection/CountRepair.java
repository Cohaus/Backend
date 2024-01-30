package gdsc.sc.bsafe.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountRepair {

    private Long legalDistrict;
    private String district;
    private long count;

    public CountRepair(Long legalDistrict, String sido, String gu, String dong, long count) {
        this.legalDistrict = legalDistrict;
        this.district = sido + " " + gu + " " + dong;
        this.count = count;
    }

}
