package io.brunoonofre64.domain.entities;

import io.brunoonofre64.domain.enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_USER")
@SequenceGenerator(name = "sequenceUser", sequenceName = "SQ_USER", allocationSize = 1)
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceUser")
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN", nullable = false, length = 30)
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 16)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Roles role;

    public UserEntity(String uuid, LocalDateTime inclusionDate, LocalDateTime modifyDate,
                      Long id, Roles role) {
        super(uuid, inclusionDate, modifyDate);
        this.id = id;
        this.role = role;
    }
}
