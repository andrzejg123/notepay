package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.PaymentDto;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentDto, String token);

}
