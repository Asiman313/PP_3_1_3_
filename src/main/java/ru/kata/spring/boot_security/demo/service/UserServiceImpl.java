package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRep;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRep userRep;


    @Autowired
    public UserServiceImpl(UserRep userRep) {
        this.userRep = userRep;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRep.findAll();
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRep.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRep.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User readUser(long id) {
        return userRep.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRep.delete(userRep.findById(id).get());

    }


    public User findByName(String name) {
        return userRep.findByUserName(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRep.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Юзер не найден");
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean existsByUsername(String username) {
        User user = userRep.findByUserName(username);
        return user != null;
    }
}
