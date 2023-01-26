package ru.kata.spring.boot_security.demo.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/rest/users")
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getUsersList() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userService.getAllUsersList();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User userCurrent = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id == 0) {
            return new UserDto(userCurrent);
        }
        return new UserDto(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        if (userDto.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(userDto);
        }
        User user = userMapper.mapper(userDto);
        userService.saveUser(user);
        userDto.setId(user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        if (userService.getUserById(userDto.getId()) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body(userDto);
        }
        User user = userMapper.mapper(userDto);
        userService.updateUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("The user has been successfully deleted");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleException(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Cannot find user with such ID");
    }
}
