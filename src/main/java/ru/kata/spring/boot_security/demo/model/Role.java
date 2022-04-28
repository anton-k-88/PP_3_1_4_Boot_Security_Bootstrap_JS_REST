package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import javax.persistence.Entity;



@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;
//    @Column(unique = true)
    private String role;

    public Role(){}
    public Role(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String name) {
        this.role = name;
    }
    @Override
    public String getAuthority() {
        return getRole();
    }
}