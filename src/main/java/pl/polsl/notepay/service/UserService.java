package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.UserDto;

public interface UserService {

    UserDto registerUser(UserDto user);

    UserDto getUserByUsername(String username);

    UserDto getCurrentUser(String token);

    UserDto updateUser(UserDto user, String token);

    UserDto getUserById(String token, Long idUser);
}
