package com.toshiba.intern.usercrud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class PagesController
{
    @GetMapping("/public")
    public String publicPage(){
        return "This is a public page";
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String privatePage(){
        return "This is a private page, only those who have ROLE_ADMIN or ROLE_USER get to access this route";
    }
}
