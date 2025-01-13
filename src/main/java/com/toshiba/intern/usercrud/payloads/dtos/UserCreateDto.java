package com.toshiba.intern.usercrud.payloads.dtos;

import com.toshiba.intern.usercrud.entity.Role;

import java.util.Collection;

public class UserCreateDto
{
    public String username;
    public String password;
    public String email;
    public Collection<Role> roles;

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public Collection<Role> getRoles(){
        return roles;
    }

}
