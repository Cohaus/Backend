package gdsc.sc.bsafe.web.dto.response;

import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.enums.RecordType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicRecordResponse {
    private Long record_id;

    @Enumerated(value = EnumType.STRING)
    private RecordType type = RecordType.BASIC;

    private Long user_id;

    private String image;

    private String title;

    private String detail;

    private String category;

    public BasicRecordResponse(Long recordId, Long userId, String image, String title, String detail, String category) {
        this.record_id = recordId;
        this.user_id = userId;
        this.image = image;
        this.title = title;
        this.detail = detail;
        this.category = category;
    }
}
