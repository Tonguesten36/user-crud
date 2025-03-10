package com.toshiba.intern.usercrud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class PagesController
{
    @GetMapping("/public")
    @Operation(summary="Accessing a public page")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "returns \"This is a public page\"", content = @Content)
    })
    public String publicPage(){
        System.out.println("publicPage called");
        return "This is a public page";
    }

    @GetMapping("/private")
    @Operation(summary="Accessing a private page that requires login beforehand")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "returns \"This is a private page, only those who have ROLE_ADMIN or ROLE_USER get to access this route\"",
            content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String privatePage(){
        System.out.println("privatePage called");
        return "This is a private page, only those who have ROLE_ADMIN or ROLE_USER get to access this route";
    }
}
