package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Payment;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PaymentDto extends SimplePaymentDto {

    private List<PaymentPartDto> paymentParts;

    private List<ChargeDto> charges;

    public PaymentDto(Payment payment) {
        super(payment);
        this.paymentParts = payment.getPaymentParts() != null ?
                payment.getPaymentParts().stream().map(PaymentPartDto::new).collect(Collectors.toList()) :
                Collections.emptyList();
        this.charges = payment.getCharges().stream().map(ChargeDto::new).collect(Collectors.toList());
    }

}
