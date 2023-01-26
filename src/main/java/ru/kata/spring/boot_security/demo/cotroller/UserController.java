package ru.kata.spring.boot_security.demo.cotroller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getTemplateForUserList(ModelMap model) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", current);
        System.out.println(current);
        return "user/user";
    }
}