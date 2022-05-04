package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Repository
public interface RoleCrudRepository extends JpaRepository<Role, Long> {
    public Role getRoleByRoleName(String roleName);
    public List<Role> findAllByRoleNameNotNull();
}


