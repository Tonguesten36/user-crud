package com.toshiba.intern.usercrud.repository;

import com.toshiba.intern.usercrud.entity.Role;
import com.toshiba.intern.usercrud.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(ERole name);


}
