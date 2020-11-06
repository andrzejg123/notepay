package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.PaymentPart;

@Data
@NoArgsConstructor
public class PaymentPartDto {

    private Long id;

    private Long idPayment;

    private String name;

    private Double value;

    public PaymentPartDto(PaymentPart paymentPart) {
        this.id = paymentPart.getId();
        this.idPayment = paymentPart.getPayment().getId();
        this.name = paymentPart.getName();
        this.value = paymentPart.getValue();
    }

}
