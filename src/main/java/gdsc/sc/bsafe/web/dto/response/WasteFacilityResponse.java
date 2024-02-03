package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.WasteFacility;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WasteFacilityResponse {

    @Schema(description = "폐기물 처리시설")
    private String name;

    @Schema(description = "상세 주소")
    private String address;

    @Schema(description = "전화번호")
    private String tel;

    public WasteFacilityResponse(WasteFacility wasteFacility){
        this.name = wasteFacility.getName();
        this.address = wasteFacility.getAddress();
        this.tel  = wasteFacility.getTel();
    }
}
