package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "group_invitations")
@Data
@NoArgsConstructor
public class GroupInvitation extends BaseEntity {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Group group;

}
