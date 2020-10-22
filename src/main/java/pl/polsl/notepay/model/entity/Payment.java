package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.notepay.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "payments")
public class Payment extends BaseModel {

    @Column
    private LocalDateTime createDate;

    @Column
    private String description;

    @Column
    private Double ownerInvolveLevel;

    @Column
    private Double amount;

    @Column
    private Integer membersNumber;

    @OneToMany(mappedBy = "payment")
    private Set<Charge> charges;

}
