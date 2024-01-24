package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.enums.RepairStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairAIRecordResponse {
    private Long record_id;

    private String image;

    private String title;

    private String place_id;

    private String address;

    private String detail;

    private Integer grade;

    private String category;

    private RepairStatus status;

    private String tel;
}
