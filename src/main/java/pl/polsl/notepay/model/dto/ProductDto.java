package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Product;

@Data
@NoArgsConstructor
public class ProductDto {

    Long id;

    String name;

    Double defaultValue;

    Long idUser;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.defaultValue = product.getDefaultValue();
        this.idUser = product.getUser().getId();
    }

}
