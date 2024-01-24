package gdsc.sc.bsafe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("AI")
public class AIRecord extends Record {

    @Column(nullable = false)
    private Integer grade;

    @Builder
    public AIRecord(User user, String image, String title, String detail, String category, Integer grade){
        super(user, image, title, detail, category);
        this.grade = grade;
    }

}
