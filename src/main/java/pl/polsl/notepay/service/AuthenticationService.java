package pl.polsl.notepay.service;

import pl.polsl.notepay.model.auth.Credentials;
import pl.polsl.notepay.model.auth.Token;
import pl.polsl.notepay.model.dto.UserDto;

public interface AuthenticationService {

    Token login(Credentials credentials);

}
