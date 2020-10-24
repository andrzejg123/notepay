package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.NotAuthenticatedException;
import pl.polsl.notepay.model.auth.Credentials;
import pl.polsl.notepay.model.auth.Token;
import pl.polsl.notepay.service.AuthenticationService;
import pl.polsl.notepay.util.AuthenticationUtils;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationUtils authenticationUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public Token login(Credentials credentials) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
            return authenticationUtils.getToken(credentials);
        } catch (AuthenticationException e) {
            throw new NotAuthenticatedException("Wrong credentials");
        }
    }
}
