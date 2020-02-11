package de.stea1th.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@CrossOrigin
@Slf4j
@RequestMapping("/api/authenticate")
@RestController
public class AuthenticationController {


    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public String getAdmin(Authentication authentication) {
//        Collection<SimpleGrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().
        KeycloakPrincipal principal = (KeycloakPrincipal)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("User with name: {}  was called", principal.getName());
        return principal.getName();
    }


}
