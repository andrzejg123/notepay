package pl.polsl.notepay.model.entity;

import lombok.*;
import pl.polsl.notepay.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double defaultValue;

    @ManyToOne(optional = false)
    private User user;

}
