package com.iolyoliveira.desafio05.services;

import com.iolyoliveira.desafio05.dto.UserDTO;
import com.iolyoliveira.desafio05.entities.Role;
import com.iolyoliveira.desafio05.entities.User;
import com.iolyoliveira.desafio05.projections.UserDetailsProjection;
import com.iolyoliveira.desafio05.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }

        User user = new User();
        user.setEmail(result.getFirst().getUsername());
        user.setPassword(result.getFirst().getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwtPrincipal)) {
                throw new UsernameNotFoundException("User not authenticated");
            }
            String username = jwtPrincipal.getClaim("username");
            return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getUserLogged() {
        User user = authenticated();
        return new UserDTO(user);
    }
}
