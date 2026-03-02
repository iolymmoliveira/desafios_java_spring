package com.iolyoliveira.desafio05.controllers;

import com.iolyoliveira.desafio05.dto.UserDTO;
import com.iolyoliveira.desafio05.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getUserLogged() {
        UserDTO dto = service.getUserLogged();
        return ResponseEntity.ok(dto);
    }
}
