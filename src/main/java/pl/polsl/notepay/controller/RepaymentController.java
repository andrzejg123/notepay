package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.RepaymentRequestDto;
import pl.polsl.notepay.service.RepaymentService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/repayments")
@AllArgsConstructor
public class RepaymentController {

    private final RepaymentService repaymentService;

    @PostMapping
    public ResponseEntity<Void> requestForRepayment(@ApiIgnore @RequestHeader("Authorization") String token,
                                                    @RequestBody RepaymentRequestDto repaymentRequestDto) {
        repaymentService.requestForRepayment(repaymentRequestDto, token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "{idRepayment}/cancel")
    public ResponseEntity<Void> cancelRepayment(@ApiIgnore @RequestHeader("Authorization") String token,
                                                @PathVariable Long idRepayment) {
        repaymentService.cancelRepayment(idRepayment, token);
        return ResponseEntity.noContent().build();
    }

}
