package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserCrudRepository;

import java.util.List;


@Service
@Transactional
public class UserServiceImplBySpringBootDataJPA implements UserService {

    private final UserCrudRepository userCrudRepository;

    public UserServiceImplBySpringBootDataJPA(UserCrudRepository repository) {
        this.userCrudRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userCrudRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        return user;
    }

    @Override
    public User saveUser(User user) {
        return userCrudRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userCrudRepository.findById(id).get();
    }

    @Override
    public void deleteUser(Long id) {
        userCrudRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersList() {
        return userCrudRepository.findAll();
    }

}
