package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.notepay.model.auth.Credentials;
import pl.polsl.notepay.model.auth.Token;
import pl.polsl.notepay.service.AuthenticationService;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<Token> login(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(authenticationService.login(credentials));
    }

}
