package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.UserDto;
import pl.polsl.notepay.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user,
                                              @ApiIgnore @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUser(user, token));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<UserDto> getUserById(@ApiIgnore @RequestHeader("Authorization") String token,
                                               @PathVariable Long idUser) {
        return ResponseEntity.ok(userService.getUserById(token, idUser));
    }

    @GetMapping(value = "/current")
    public ResponseEntity<UserDto> getCurrentUser(@ApiIgnore @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getCurrentUser(token));
    }

}
