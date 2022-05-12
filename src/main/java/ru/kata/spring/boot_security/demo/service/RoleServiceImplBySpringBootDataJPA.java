package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleCrudRepository;

import java.util.List;

@Service
@Transactional
public class RoleServiceImplBySpringBootDataJPA implements RoleService {

    private final RoleCrudRepository roleCrudRepository;

    public RoleServiceImplBySpringBootDataJPA(RoleCrudRepository roleCrudRepository) {
        this.roleCrudRepository = roleCrudRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleCrudRepository.findAll();
    }

    @Override
    public Role getRole(Long id) {
        return roleCrudRepository.getById(id);
    }

    @Override
    public void saveRole(Role role) {
        roleCrudRepository.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleCrudRepository.deleteById(role.getId());
    }
}
