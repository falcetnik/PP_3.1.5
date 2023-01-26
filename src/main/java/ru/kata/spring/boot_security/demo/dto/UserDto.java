package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {
    private RoleRepository roleRepository;

    private Long id;

    private String login;

    private String email;

    private String password;

    private String name;

    private String lastName;

    private int age;

    private String userDetailsName;

    private Set<String> roles;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserDetailsName() {
        return userDetailsName;
    }

    public void setUserDetailsName(String userDetailsName) {
        this.userDetailsName = userDetailsName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(login, userDto.login) && Objects.equals(email, userDto.email) && Objects.equals(password, userDto.password) && Objects.equals(name, userDto.name) && Objects.equals(lastName, userDto.lastName) && Objects.equals(age, userDto.age) && Objects.equals(userDetailsName, userDto.userDetailsName) && Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, name, lastName, age, userDetailsName, roles);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", userDetailsName='" + userDetailsName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
