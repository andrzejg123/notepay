package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.BalanceDto;
import pl.polsl.notepay.service.BalanceService;

@RestController
@RequestMapping(value = "/balances")
@AllArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping
    public ResponseEntity<BalanceDto> getBalanceWithUser(@RequestHeader("Authorization") String token, @RequestParam("idUser") Long idUser) {
        return ResponseEntity.ok(balanceService.getBalanceWithUser(idUser, token));
    }

}
