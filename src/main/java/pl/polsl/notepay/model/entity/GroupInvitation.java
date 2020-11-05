package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "group_invitations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInvitation extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Group group;

}
