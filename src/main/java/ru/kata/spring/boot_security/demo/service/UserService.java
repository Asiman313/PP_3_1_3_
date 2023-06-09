package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(User user);

    User readUser(long id);

    void deleteUser(long id);

    User findByName(String name);

    public boolean existsByUsername(String username);
    public void updateUser(User user);
}
