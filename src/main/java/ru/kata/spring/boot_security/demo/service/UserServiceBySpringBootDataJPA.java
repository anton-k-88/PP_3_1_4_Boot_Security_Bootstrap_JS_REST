package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserCrudRepository;


import java.util.List;


@Service
@Transactional
public class UserServiceBySpringBootDataJPA implements UserService {

    private final UserCrudRepository repository;

    public UserServiceBySpringBootDataJPA(UserCrudRepository repository) {
        this.repository = repository;
    }


    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return repository.getById(id);
    }

    @Override
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    public List<User> getUsersList() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void saveAllUsers(List<User> userList) {
        repository.saveAll(userList);
    }
}
