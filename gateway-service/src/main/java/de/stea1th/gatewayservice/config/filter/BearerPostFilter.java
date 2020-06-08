//package de.stea1th.gatewayservice.config.filter;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class BearerPostFilter extends KeycloakFilter {
//
//    @Override
//    protected boolean isPostFilter() {
//        return true;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return super.shouldFilter() && !getResponse().containsHeader(AUTHORIZATION);
//    }
//
//    @Override
//    public Object run() {
//        getResponse().addHeader(AUTHORIZATION, extractBearer());
//        return null;
//    }
//}
