package de.stea1th.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
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
//    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public String getAdmin(Authentication authentication) {

        KeycloakSecurityContext securityContext = ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext();

        AccessToken accessToken = securityContext.getToken();

        String email = accessToken.getEmail();
        String userName = accessToken.getFamilyName();

        log.info("User with family name: {} and email: {} was called", userName, email);

        accessToken.setFamilyName("Ivanov");


        return "Hallo";
    }


}
