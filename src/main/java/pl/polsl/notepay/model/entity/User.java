package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private String description;

    @Column(nullable = false)
    private Boolean deleted;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "user")
    private Set<Charge> charges;

    @ManyToMany(mappedBy = "users")
    private List<Group> groups;

    @OneToMany(mappedBy = "user")
    private List<GroupInvitation> groupInvitations;

    @OneToMany(mappedBy = "user")
    private Set<Product> products;

    @OneToMany(mappedBy = "initiator")
    private List<Repayment> ownRepayments;

    @OneToMany(mappedBy = "recipient")
    private List<Repayment> givenRepayments;

    @OneToMany(mappedBy = "owner")
    private List<Payment> payments;

}
