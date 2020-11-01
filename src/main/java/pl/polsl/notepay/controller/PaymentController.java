package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.PaymentDto;
import pl.polsl.notepay.service.PaymentService;

@RestController
@RequestMapping(value = "/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestHeader("Authorization") String token,
                                                    @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDto, token));
    }

}
