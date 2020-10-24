package pl.polsl.notepay.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.polsl.notepay.model.auth.Credentials;
import pl.polsl.notepay.model.auth.Token;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
@AllArgsConstructor
public class AuthenticationUtils {

    private static final int VALIDATION_TIME = 168 * 3600; //7 days
    private static final String SECRET = "notepay_23XC23VBB65";
    private final UserRepository userRepository;


    private User getUserFromSubject(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("Wrong token")
        );
    }

    public User getUserFromToken(String token) {
        if(token.startsWith("Bearer "))
            token = token.substring("Bearer ".length());

        String emailName = getSubjectFromToken(token);
        return getUserFromSubject(emailName);
    }

    public Token getToken(Credentials credentials) {
        Token token = new Token();
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(VALIDATION_TIME);

        token.setToken(
                JWT.create()
                        .withIssuedAt(new Date())
                        .withSubject(credentials.getUsername())
                        .withExpiresAt(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                        .sign(HMAC512(SECRET.getBytes()))
        );

        token.setExpirationDate(expirationDate);
        return token;
    }

    public static String getSubjectFromToken(String token) {
        DecodedJWT decoded = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token);

        return decoded.getSubject();
    }
}
