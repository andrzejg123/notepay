package pl.polsl.notepay.model.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credentials {

    private String username;

    private String password;

}
