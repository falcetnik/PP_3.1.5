package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;

@Component
public class InitUserAndAdmin {

    private final UserService userService;

    @Autowired
    public InitUserAndAdmin(UserService userService) {
        this.userService = userService;
    }

    public void createByBaseAdmin() {
        UserDto admin = new UserDto();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@mail.ru");
        HashSet<String> roles = new HashSet<>();
        roles.add("ADMIN");
        roles.add("USER");
        admin.setRoles(roles);
        userService.saveUser(admin);
    }

    public void createByBaseUser() {
        UserDto user = new UserDto();
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user@mail.ru");
        HashSet<String> roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);
        userService.saveUser(user);
    }
}
