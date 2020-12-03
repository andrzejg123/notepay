package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.PaymentDto;
import pl.polsl.notepay.model.dto.SimplePaymentDto;
import pl.polsl.notepay.service.PaymentService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@ApiIgnore @RequestHeader("Authorization") String token,
                                                    @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDto, token));
    }

    @GetMapping(value = "/own")
    public ResponseEntity<List<SimplePaymentDto>> getOwnPayments(@ApiIgnore @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(paymentService.getOwnPayments(token));
    }

}
