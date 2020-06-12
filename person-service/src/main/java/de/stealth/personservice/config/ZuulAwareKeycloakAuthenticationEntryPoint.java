package de.stealth.personservice.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Zuul HTTP <code>x-forwarded-prefix</code> header aware authentication entry point.
 */
@Slf4j
public class ZuulAwareKeycloakAuthenticationEntryPoint extends KeycloakAuthenticationEntryPoint {


    public ZuulAwareKeycloakAuthenticationEntryPoint(AdapterDeploymentContext adapterDeploymentContext) {
        super(adapterDeploymentContext);
    }

    protected void commenceLoginRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String prefix = request.getHeader("x-forwarded-prefix");
        UriComponents components =
                ServletUriComponentsBuilder.fromRequest(request)
                        .replacePath((prefix != null ? prefix : "") + "/sso/login").build();

        log.info("Redirecting to login URI: {}", components.toUriString());
        response.sendRedirect(components.toUriString());
    }
}
