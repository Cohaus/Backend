package gdsc.sc.bsafe.domain.mapping;

import gdsc.sc.bsafe.domain.Organization;
import gdsc.sc.bsafe.domain.User;
import gdsc.sc.bsafe.domain.common.BaseTimeEntity;
import gdsc.sc.bsafe.domain.enums.VolunteerType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "volunteer")
public class Volunteer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteer_id")
    private Long volunteerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VolunteerType type;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Builder
    public Volunteer(User user, VolunteerType type) {
        this.user = user;
        this.type = type;
    }

    public void updateOrganization(Organization organization) {
        this.organization = organization;
    }

    public void updateVolunteerType(VolunteerType type) {
        this.type = type;
    }

}
