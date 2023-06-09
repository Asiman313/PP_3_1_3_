package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String redirect() {
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        User user = userService.findByName(principal.getName());
        model.addAttribute("user", userService.readUser(user.getId()));
        System.out.println("Успешно: user id" + user.getClass());
        model.addAttribute("titleTable", "Страница пользователя: ");
        return "user";
    }
}
