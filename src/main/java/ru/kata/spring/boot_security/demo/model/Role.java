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
    private String roleName;

    public Role(){}
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String name) {
        this.roleName = name;
    }
    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
