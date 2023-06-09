package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;



    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("titleTable1", "Список всех пользователей:");
        return "admin";
    }

    @GetMapping("/{id}")
    public String readUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.readUser(id));
        model.addAttribute("titleTable", "Страница пользователя:");
        return "user";
    }

    @GetMapping("/new")
    public String newUser (Model model, @ModelAttribute("user") User user) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("rolesAdd", roles);
        return "newUser";
    }

    @PostMapping
    public String saveNewUser(@ModelAttribute("user") User user, Model model, String password) {
        if (userService.existsByUsername(user.getUserName())) {
            model.addAttribute("error", "Пользователь с таким логином уже существует.");
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("rolesAdd", roles);
            return "newUser";
        }

        user.setPassword(passwordEncoder.encode(password));
        userService.createUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String edit (Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.readUser(id));
        model.addAttribute("rolesAdd", roleService.getAllRoles());
        return "updateUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, Model model, String password) {
        User existingUser = userService.findByName(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            model.addAttribute("error", "Пользователь с таким логином уже существует.");
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("rolesAdd", roles);
            return "newUser";
        }

        user.setPassword(passwordEncoder.encode(password));
            userService.updateUser(user);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
