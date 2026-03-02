package com.iolyoliveira.desafio05.services;

import com.iolyoliveira.desafio05.entities.User;
import com.iolyoliveira.desafio05.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(long userId) {
        User userLogged = userService.authenticated();
        if (!userLogged.hasRole("ROLE_ADMIN") && !userLogged.getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }
    }
}
