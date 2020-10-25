package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Boolean deleted;

    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "group")
    private List<GroupInvitation> groupInvitations;

    @OneToMany(mappedBy = "group")
    private List<Payment> payments;

}
