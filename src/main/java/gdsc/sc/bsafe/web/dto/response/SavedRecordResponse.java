package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.AIRecord;
import gdsc.sc.bsafe.domain.Record;
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

    public SavedRecordResponse(AIRecord aiRecord) {
        this.record_id = aiRecord.getRecordId();
        this.user_id = aiRecord.getUser().getUserId();
        this.image = aiRecord.getImage();
        this.title = aiRecord.getTitle();
        this.detail = aiRecord.getDetail();
        this.category = aiRecord.getCategory();
        this.grade = getGrade();
    }
}
