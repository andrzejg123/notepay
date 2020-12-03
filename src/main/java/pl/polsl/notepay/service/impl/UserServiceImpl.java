package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.notepay.exception.ResourceNotFoundException;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.UserDto;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.UserRepository;
import pl.polsl.notepay.service.UserService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationUtils authenticationUtils;

    @Override
    public UserDto registerUser(UserDto user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new WrongRequestException("Username already exists");

        User newUser = User.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .description(user.getDescription())
                .createDate(LocalDateTime.now())
                .password(passwordEncoder.encode(user.getPassword()))
                .deleted(false)
                .build();

        return new UserDto(userRepository.save(newUser));
    }

    @Override
    public UserDto getUserByUsername(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("There is no user with such a username"));

        return new UserDto(user);
    }

    @Override
    public UserDto getCurrentUser(String token) {
        return new UserDto(authenticationUtils.getUserFromToken(token));
    }

    @Override
    public UserDto updateUser(UserDto user, String token) {
        User currentUser = authenticationUtils.getUserFromToken(token);
        if(user.getName() != null && !user.getName().isEmpty())
            currentUser.setName(user.getName());

        if(user.getSurname() != null && !user.getSurname().isEmpty())
            currentUser.setSurname(user.getSurname());

        currentUser.setDescription(user.getDescription());
        if(user.getPassword() != null && !user.getPassword().isEmpty())
            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return new UserDto(userRepository.save(currentUser));
    }
}
