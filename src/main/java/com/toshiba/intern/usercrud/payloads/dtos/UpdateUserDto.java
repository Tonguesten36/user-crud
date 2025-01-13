package com.toshiba.intern.usercrud.payloads.dtos;

public class UpdateUserDto
{
    private String newUsername;
    private String newEmail;
    private String newPassword;

    public String getNewUsername(){
        return newUsername;
    }

    public String getNewEmail(){
        return newEmail;
    }

    public String getNewPassword(){
        return newPassword;
    }
}
