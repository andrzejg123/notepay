package pl.polsl.notepay.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Token {

    private String token;

    private LocalDateTime expirationDate;

}
