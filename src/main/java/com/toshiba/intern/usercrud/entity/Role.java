package com.toshiba.intern.usercrud.entity;

import com.toshiba.intern.usercrud.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable=false)
    @Enumerated(EnumType.STRING)
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(ERole name) {
        this.name = name;
    }

    public Role(){}

    public int getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole roleName) {
        this.name = roleName;
    }

}
