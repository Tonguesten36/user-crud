package com.toshiba.intern.usercrud.repository;

import com.toshiba.intern.usercrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM public.users", nativeQuery = true)
    List<User> findAll();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
