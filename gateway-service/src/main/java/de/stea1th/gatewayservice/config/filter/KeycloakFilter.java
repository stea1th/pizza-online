package de.stea1th.gatewayservice.config.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class KeycloakFilter extends ZuulFilter {

//    protected static final String AUTHORIZATION = "Authorization";
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return isSecureRequest();
//    }
//
//    @Override
//    public String filterType() {
//        if (isRouteFilter()) {
//            return "route";
//        }
//        if (isPreFilter()) {
//            return "pre";
//        }
//        if (isPostFilter()) {
//            return "post";
//        }
//        throw new IllegalStateException("One of isRouteFilter/isPreFilter/isPostFilter must be overriden");
//    }
//
//    protected boolean isRouteFilter() {
//        return false;
//    }
//
//    protected boolean isPreFilter() {
//        return false;
//    }
//
//    protected boolean isPostFilter() {
//        return false;
//    }
//
//    protected String extractBearer() {
//        return String.format("Bearer %s", extractToken());
//    }
//
//    protected String extractToken() {
//        Principal principal = getUserPrincipal();
//        return principal instanceof KeycloakPrincipal ? ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getTokenString() : "";
//    }
//
//    protected HttpServletResponse getResponse() {
//        return RequestContext.getCurrentContext().getResponse();
//    }
//
//    protected boolean isSecureRequest() {
//        return getUserPrincipal() instanceof KeycloakPrincipal;
//    }
//
//    protected Principal getUserPrincipal() {
//        RequestContext context = RequestContext.getCurrentContext();
//        Principal principal = context.getRequest().getUserPrincipal();
//        return principal != null ? principal : (Principal) context.get(Principal.class.getName());
//    }

    private static final String AUTHORIZATION_HEADER = "authorization";

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(AUTHORIZATION_HEADER) == null) {
            addKeycloakTokenToHeader(ctx);
        }
        return null;
    }

    private void addKeycloakTokenToHeader(RequestContext ctx) {
        RefreshableKeycloakSecurityContext securityContext = getRefreshableKeycloakSecurityContext(ctx);
        if (securityContext != null) {
            ctx.addZuulRequestHeader(AUTHORIZATION_HEADER, buildBearerToken(securityContext));
        }
    }

    private RefreshableKeycloakSecurityContext getRefreshableKeycloakSecurityContext(RequestContext ctx) {
        if (ctx.getRequest().getUserPrincipal() instanceof KeycloakAuthenticationToken) {
            KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) ctx.getRequest().getUserPrincipal();
            return (RefreshableKeycloakSecurityContext) token.getCredentials();
        }
        return null;
    }

    private String buildBearerToken(RefreshableKeycloakSecurityContext securityContext) {
        return "Bearer " + securityContext.getTokenString();
    }
}
