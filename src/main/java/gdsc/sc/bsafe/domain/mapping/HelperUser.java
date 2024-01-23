package gdsc.sc.bsafe.domain.mapping;

import gdsc.sc.bsafe.domain.HelperGroup;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.common.BaseTimeEntity;
import gdsc.sc.bsafe.domain.enums.HelperType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "helper_user")
public class HelperUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "helper_user_id")
    private Long helperId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private HelperType type;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private HelperGroup helperGroup;

}
