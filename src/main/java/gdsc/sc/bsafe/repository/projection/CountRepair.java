package gdsc.sc.bsafe.repository.projection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountRepair {

    @Schema(description = "법정동 pk", example = "1")
    private Long legalDistrict;

    @Schema(description = "지역", example = "서울시 광진구 군자동")
    private String district;

    @Schema(description = "해당 지역의 수리 요청 개수", example = "5")
    private long count;

    public CountRepair(Long legalDistrict, String sido, String gu, String dong, long count) {
        this.legalDistrict = legalDistrict;
        this.district = sido + " " + gu + " " + dong;
        this.count = count;
    }

}
