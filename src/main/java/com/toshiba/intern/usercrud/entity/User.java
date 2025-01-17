package com.toshiba.intern.usercrud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Collection;

@Getter
@Entity
@Data
@Builder
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Setter
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Setter
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    @JsonIgnoreProperties("users") // Prevent recursion but still include other fields like `name`
    private Collection<Role> roles;

    public User() {

    }

    public User(int id, String username, String email, String password, Collection<Role> roles)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String email, String password, Collection<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
    }

}
