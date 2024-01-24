package gdsc.sc.bsafe.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavedRecordResponse {
    private Long record_id;

    private Long user_id;

    private String image;

    private String title;

    private String detail;

    private String category;

    private Integer grade;

}
