package pl.polsl.notepay.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.notepay.model.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String description;

    private LocalDateTime createDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.description = user.getDescription();
        this.createDate = user.getCreateDate();
    }

}
