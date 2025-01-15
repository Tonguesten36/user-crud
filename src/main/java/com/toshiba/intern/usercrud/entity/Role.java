package com.toshiba.intern.usercrud.entity;

import com.toshiba.intern.usercrud.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Data
@Getter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Setter
    @Getter
    @Column(name = "name", nullable=false)
    @Enumerated(EnumType.STRING)
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(ERole name) {
        this.name = name;
    }

    public Role(){}

}
