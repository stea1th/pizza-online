//package de.stea1th.gatewayservice.config.filter;
//
//import com.netflix.zuul.context.RequestContext;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthorizationRouteFilter extends KeycloakFilter {
//
//    @Override
//    protected boolean isRouteFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext.getCurrentContext().addZuulRequestHeader(AUTHORIZATION, extractBearer());
//        return null;
//    }
//}
