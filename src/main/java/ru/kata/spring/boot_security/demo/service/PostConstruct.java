package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostConstruct {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public PostConstruct(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @javax.annotation.PostConstruct
    public void init() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        roleService.saveRole(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleService.saveRole(roleUser);

        List<Role> roleSet1 = new ArrayList<>();
        roleSet1.add(roleAdmin);
        roleSet1.add(roleUser);
        List<Role> roleSet2 = new ArrayList<>();
        roleSet2.add(roleUser);
        List<Role> roleSet3 = new ArrayList<>();
        roleSet3.add(roleUser);
        List<Role> roleSet4 = new ArrayList<>();
        roleSet4.add(roleAdmin);
        List<Role> roleSet5 = new ArrayList<>();
        roleSet5.add(roleUser);

        userService.saveUser(new User("Иван", "Петров", 18, "ivan@mail.ru", "ivan", roleSet1));
        userService.saveUser(new User("Петр", "Ольгин", 19, "petr@mail.ru", "petr", roleSet2));
        userService.saveUser(new User("Ольга", "Игорева", 20, "olga@mail.ru", "olga", roleSet3));
        userService.saveUser(new User("Игорь", "Алимов", 21, "igor@mail.ru", "igor", roleSet4));
        userService.saveUser(new User("Алина", "Аревадзе", 22, "alina@mail.ru", "alina", roleSet5));
    }


}
