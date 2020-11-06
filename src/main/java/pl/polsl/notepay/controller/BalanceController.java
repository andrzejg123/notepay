package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.BalanceDto;
import pl.polsl.notepay.service.BalanceService;

import java.util.List;

@RestController
@RequestMapping(value = "/balances")
@AllArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping(value = "/user")
    public ResponseEntity<BalanceDto> getBalanceWithUser(@RequestHeader("Authorization") String token,
                                                         @RequestParam("idUser") Long idUser) {
        return ResponseEntity.ok(balanceService.getBalanceWithUser(idUser, token));
    }

    @GetMapping(value = "/group")
    public ResponseEntity<List<BalanceDto>> getBalanceWithGroupMembers(@RequestHeader("Authorization") String token,
                                                                       @RequestParam("idGroup") Long idGroup) {
        return ResponseEntity.ok(balanceService.getBalanceWithGroupMembers(idGroup, token));
    }

}
