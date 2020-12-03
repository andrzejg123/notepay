package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.RepaymentRequestDto;

public interface RepaymentService {

    void requestForRepayment(RepaymentRequestDto repaymentRequestDto, String token);

    void cancelRepayment(Long idRepayment, String token);
}
