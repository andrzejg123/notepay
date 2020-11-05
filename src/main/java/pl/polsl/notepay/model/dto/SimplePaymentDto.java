package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.Payment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SimplePaymentDto {

    private Long id;

    private LocalDateTime createDate;

    private String description;

    private Double ownerInvolveLevel;

    private Double ownerProgress;

    private Double amount;

    private Double ownerAmount;

    private Integer membersNumber;

    private Long idState;

    private Long idGroup;

    private Long idOwner;

    public SimplePaymentDto(Payment payment) {
        this.id = payment.getId();
        this.createDate = payment.getCreateDate();
        this.description = payment.getDescription();
        this.ownerInvolveLevel = payment.getOwnerInvolveLevel();
        this.ownerProgress = payment.getOwnerProgress();
        this.amount = payment.getAmount();
        this.ownerAmount = payment.getOwnerAmount();
        this.membersNumber = payment.getMembersNumber();
        this.idState = payment.getState().getId();
        this.idGroup = payment.getGroup() != null ? payment.getGroup().getId() : null;
        this.idOwner = payment.getOwner().getId();

    }

}
