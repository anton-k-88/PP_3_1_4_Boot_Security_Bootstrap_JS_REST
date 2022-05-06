package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleCrudRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class PostConstruct {

    private UserService userService;
    private RoleCrudRepository roleCrudRepository;

    @Autowired
    public PostConstruct(UserService userService, RoleCrudRepository roleCrudRepository) {
        this.userService = userService;
        this.roleCrudRepository = roleCrudRepository;
    }

    @javax.annotation.PostConstruct
    public void init() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        roleCrudRepository.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleCrudRepository.save(roleUser);

        List<Role> roleSet1 = new ArrayList<>();
        roleSet1.add(roleAdmin);
        roleSet1.add(roleUser);
        List<Role> roleSet2 = new ArrayList<>();
        roleSet2.add(roleUser);
        List<Role> roleSet3 = new ArrayList<>();
        roleSet3.add(roleUser);
        List<Role> roleSet4 = new ArrayList<>();
        roleSet4.add(roleUser);
        List<Role> roleSet5 = new ArrayList<>();
        roleSet5.add(roleUser);

        userService.saveUser(new User("Иван", "Петров", 18, "ivan@mail.ru", "ivan", roleSet1));
        userService.saveUser(new User("Петр", "Ольгин", 19, "petr@mail.ru", "petr", roleSet2));
        userService.saveUser(new User("Ольга", "Игорева", 20, "olga@mail.ru", "olga", roleSet3));
        userService.saveUser(new User("Игорь", "Алимов", 21, "igor@mail.ru", "igor", roleSet4));
        userService.saveUser(new User("Алина", "Аревадзе", 22, "alina@mail.ru", "alina", roleSet5));
    }


}
