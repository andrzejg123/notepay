package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.PaymentPart;

@Data
@NoArgsConstructor
public class PaymentPartDto {

    private Long id;

    private Long idPayment;

    private Long idProduct;

    private Double value;

    public PaymentPartDto(PaymentPart paymentPart) {
        this.id = paymentPart.getId();
        this.idPayment = paymentPart.getPayment().getId();
        this.idProduct = paymentPart.getProduct() != null ? paymentPart.getProduct().getId() : null;
        this.value = paymentPart.getValue();
    }

}
