package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.notepay.model.BaseModel;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "charges")
public class Charge extends BaseModel {

    @Column
    private Double progressLevel;

    @Column
    private Double amount;

    @Column
    private Double involveLevel;

    @ManyToOne
    private User user;

    @ManyToOne
    private Payment payment;

}
