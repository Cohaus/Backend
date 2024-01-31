package gdsc.sc.bsafe.domain;

import gdsc.sc.bsafe.domain.enums.AIGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("AI")
public class AIRecord extends Record {

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private AIGrade grade;

    @Builder
    public AIRecord(User user, String image, String title, String detail, String category, AIGrade grade){
        super(user, image, title, detail, category);
        this.grade = grade;
    }

}
