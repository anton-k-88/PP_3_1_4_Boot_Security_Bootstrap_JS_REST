package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Gender;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.LinkedHashSet;
import java.util.Set;


@Component
public class PostConstruct {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @javax.annotation.PostConstruct
    public void init() {

        Set<Role> roleSet1 = new LinkedHashSet<>();
        roleSet1.add(new Role("ROLE_USER"));
        roleSet1.add(new Role("ROLE_ADMIN"));

        Set<Role> roleSet2 = new LinkedHashSet<>();
        roleSet2.add(new Role("ROLE_USER"));

        Set<Role> roleSet3 = new LinkedHashSet<>();
        roleSet2.add(new Role("ROLE_USER"));

        Set<Role> roleSet4 = new LinkedHashSet<>();
        roleSet2.add(new Role("ROLE_USER"));

        Set<Role> roleSet5 = new LinkedHashSet<>();
        roleSet2.add(new Role("ROLE_USER"));


        userService.saveUser(new User("Иван", "Петров", Gender.male, "+79050987654", "ivan@mail.ru", "ivan", "ivan", roleSet1));
        userService.saveUser(new User("Петр", "Ольгин", Gender.male, "+79031234567", "petr@mail.ru", "petr", "petr", roleSet2));
        userService.saveUser(new User("Ольга", "Игорева", Gender.female, "+79265545544", "olga@mail.ru", "olga", "olga", roleSet3));
        userService.saveUser(new User("Игорь", "Алимов", Gender.male, "+79234446688", "igor@mail.ru", "igor", "igor", roleSet4));
        userService.saveUser(new User("Алина", "Аревадзе", Gender.female, "+74957772211", "alina@mail.ru", "alina", "alina", roleSet5));
    }


}
