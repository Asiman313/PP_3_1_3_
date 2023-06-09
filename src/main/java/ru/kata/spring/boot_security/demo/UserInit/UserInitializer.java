package ru.kata.spring.boot_security.demo.UserInit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRep;
import ru.kata.spring.boot_security.demo.repositories.UserRep;

import java.util.Collections;
import java.util.Set;

@Component
public class UserInitializer implements CommandLineRunner {

    private final UserRep userRep;
    private final RoleRep roleRep;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInitializer(UserRep userRep, RoleRep roleRep, PasswordEncoder passwordEncoder) {
        this.userRep = userRep;
        this.roleRep = roleRep;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUserName("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setName("user");
        user.setAge(20);
        user.setLastName("user");

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleRep.save(userRole);
        roleRep.save(adminRole);
        user.setRoles(Collections.singleton(adminRole));
        userRep.save(user);

    }
}
