package gdsc.sc.bsafe.domain;

import gdsc.sc.bsafe.domain.common.BaseTimeEntity;
import gdsc.sc.bsafe.domain.enums.Authority;
import gdsc.sc.bsafe.domain.mapping.Repair;
import gdsc.sc.bsafe.global.security.Password;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String id;

    @Embedded
    private Password password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String tel;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.USER;

    @OneToMany(mappedBy = "volunteer")
    private List<Repair> repairList = new ArrayList<>();

    @Builder
    public User(String id, Password password, String email, String tel, String name) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.name = name;
        this.authority = Authority.USER;
    }
}
