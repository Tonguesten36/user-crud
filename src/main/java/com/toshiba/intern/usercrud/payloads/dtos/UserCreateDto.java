package com.toshiba.intern.usercrud.payloads.dtos;

import com.toshiba.intern.usercrud.entity.Role;
import lombok.Getter;

import java.util.Collection;

@Getter
public class UserCreateDto
{
    public String username;
    public String password;
    public String email;
    public Collection<Role> roles;

}
