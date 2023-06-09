package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRep;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRep roleRep;

    @Autowired
    public RoleServiceImpl(RoleRep roleRep) {
        this.roleRep = roleRep;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRep.findAll();
    }


}
