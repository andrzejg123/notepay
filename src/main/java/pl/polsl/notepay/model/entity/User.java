package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.notepay.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private Boolean deleted;

    @Column
    private LocalDateTime createDate;

}
