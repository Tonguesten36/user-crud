package com.toshiba.intern.usercrud.payloads.dtos;

import lombok.Getter;

@Getter
public class UpdateUserDto
{
    private String newUsername;
    private String newEmail;
    private String newPassword;

}
