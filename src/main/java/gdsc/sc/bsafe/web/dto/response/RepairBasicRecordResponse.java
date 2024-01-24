package gdsc.sc.bsafe.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairBasicRecordResponse {
    private Long record_id;

    private String image;

    private String title;

    private String place_id;

    private String address;

    private String detail;

    private String category;

    private String status;
}
