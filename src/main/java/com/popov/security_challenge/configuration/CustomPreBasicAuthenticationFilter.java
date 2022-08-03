package com.popov.security_challenge.configuration;

import com.popov.security_challenge.configuration.security_properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
/**
 * Used to avoid storing clientId and secret in client app (if it is the only app!)
 */
public class CustomPreBasicAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_HOST="/oauth/token";

    private final SecurityProperties securityProperties;

    public CustomPreBasicAuthenticationFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    private class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public CustomHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getHeader(String name) {
            //log.debug("securityProperties = {}", securityProperties);
            if ("Authorization".equals(name)) {
                //Customizing header for each /oauth/token request
                SecurityProperties.JwtProperties jwt = securityProperties.getJwt();
                return "Basic " + Base64.getEncoder().encodeToString((jwt.getClientId() + ":" + jwt.getSecret()).getBytes());
            }
            return super.getHeader(name);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = httpServletRequest.getServletPath();
        if (servletPath.startsWith(AUTH_HOST)) {
            CustomHttpServletRequestWrapper wrappedRequest =
                    new CustomHttpServletRequestWrapper(httpServletRequest);
            filterChain.doFilter(wrappedRequest, httpServletResponse);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

}
