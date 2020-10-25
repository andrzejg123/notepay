package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Payment;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    private LocalDateTime createDate;

    private String description;

    private Double ownerInvolveLevel;

    private Double ownerProgress;

    private Double amount;

    private Integer membersNumber;

    private Long idState;

    private Long idGroup;

    private List<PaymentPartDto> paymentParts;

    private List<ChargeDto> charges;

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.createDate = payment.getCreateDate();
        this.description = payment.getDescription();
        this.ownerInvolveLevel = payment.getOwnerInvolveLevel();
        this.ownerProgress = payment.getOwnerProgress();
        this.amount = payment.getAmount();
        this.membersNumber = payment.getMembersNumber();
        this.idState = payment.getState().getId();
        this.idGroup = payment.getGroup() != null ? payment.getGroup().getId() : null;
        this.paymentParts = payment.getPaymentParts() != null ?
                payment.getPaymentParts().stream().map(PaymentPartDto::new).collect(Collectors.toList()) :
                Collections.emptyList();
        this.charges = payment.getCharges().stream().map(ChargeDto::new).collect(Collectors.toList());
    }

}
