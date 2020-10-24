package pl.polsl.notepay.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.BaseEntity;
import pl.polsl.notepay.model.enumeration.StateEnum;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "states")
@Data
@NoArgsConstructor
public class State extends BaseEntity {

    @Column
    @Enumerated(value = EnumType.STRING)
    StateEnum name;

}
