package de.stea1th.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
