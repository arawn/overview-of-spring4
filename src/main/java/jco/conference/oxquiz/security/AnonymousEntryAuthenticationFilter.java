package jco.conference.oxquiz.security;

import jco.conference.oxquiz.model.Player;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

public class AnonymousEntryAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new AnonymousEntryHttpServletRequest(request), response);
    }

    class AnonymousEntryHttpServletRequest extends HttpServletRequestWrapper {

        private static final String SESSION_AUTHENTICATION_KEY = "authentication";
        private final Player principal;

        public AnonymousEntryHttpServletRequest(HttpServletRequest request) {
            super(request);

            if(isAuthenticated()) {
                principal = (Player) getSession().getAttribute(SESSION_AUTHENTICATION_KEY);
            } else {
                principal = new Player(UUID.randomUUID().toString());
                getSession().setAttribute(SESSION_AUTHENTICATION_KEY, principal);
            }
        }

        boolean isAuthenticated() {
            return getSession().getAttribute(SESSION_AUTHENTICATION_KEY) != null;
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }

    }

}
