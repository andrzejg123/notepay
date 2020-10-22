package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.notepay.model.BaseModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class User extends BaseModel {

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String description;

    @Column
    private Boolean deleted;

    @Column
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "user")
    private Set<Charge> charges;

    @ManyToMany(mappedBy = "users")
    private List<Group> groups;

}
