package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserRep extends JpaRepository<User, Long> {
    User findByUserName(String username);

    
}
