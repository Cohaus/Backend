package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.enums.RecordType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AIRecordResponse {
    private Long record_id;

    @Enumerated(value = EnumType.STRING)
    private RecordType type = RecordType.AI;

    private Long user_id;

    private String image;

    private String title;

    private String detail;

    private String category;

    private Integer grade;

    public AIRecordResponse(Long recordId, Long userId, String image, String title, String detail, String category, Integer grade) {
        this.record_id = recordId;
        this.user_id = userId;
        this.image = image;
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.grade = grade;
    }
}
