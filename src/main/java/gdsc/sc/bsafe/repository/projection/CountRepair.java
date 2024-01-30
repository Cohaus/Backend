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

    private String district;
    private long count;

    public CountRepair(String sido, String gu, String dong, long count) {
        this.district = sido + " " + gu + " " + dong;
        this.count = count;
    }

}
