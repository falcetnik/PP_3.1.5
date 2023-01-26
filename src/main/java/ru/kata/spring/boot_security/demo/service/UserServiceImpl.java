package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, @Lazy BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user with uch name : " + login
            );
        } else {
            user.setUserDetailsName(user.getName());
            return user;
        }
    }


    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        User user = userMapper.mapper(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleService.saveRole(user.getRoles());
        userRepository.save(user);
        userDto.setId(user.getId());
        System.out.println(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        User user = userMapper.mapper(userDto);
        if (userRepository.existsById(user.getId())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveUser(userDto);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsersList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUserDtoList() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = getAllUsersList();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }
}
