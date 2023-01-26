package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {

    User findByLogin(String username) throws UsernameNotFoundException;

    @Override
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;

    void saveUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUserById(long id);

    List<User> getAllUsersList();

    User getUserById(Long id);

    List<UserDto> getUserDtoList();

}