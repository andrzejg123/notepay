package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.notepay.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "groups")
public class Group extends BaseModel {

    @Column
    private String name;

    @Column
    private LocalDateTime createDate;

    @Column
    private Boolean deleted;

    @ManyToMany
    private List<User> users;

}
