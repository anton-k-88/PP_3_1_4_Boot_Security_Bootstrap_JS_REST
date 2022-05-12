package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {

    User saveUser(User user);
    User getUser(Long id);
    void deleteUser(Long id);
    List<User> getUsersList();

}
