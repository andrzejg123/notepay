package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.PaymentDto;
import pl.polsl.notepay.model.dto.SimplePaymentDto;

import java.util.List;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentDto, String token);

    List<SimplePaymentDto> getOwnPayments(String token);

}
