package gdsc.sc.bsafe.domain;

import gdsc.sc.bsafe.domain.enums.RepairCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("BASIC")
public class BasicRecord extends Record {

    @Builder
    public BasicRecord(User user, String image, String title, String detail, RepairCategory category){
        super(user, image, title, detail, category);
    }

}
