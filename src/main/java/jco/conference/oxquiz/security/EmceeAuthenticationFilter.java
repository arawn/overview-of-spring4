package jco.conference.oxquiz.security;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmceeAuthenticationFilter extends OncePerRequestFilter {

    private static final String SESSION_AUTHENTICATION_KEY = EmceeAuthenticationFilter.class.getName();
    private UrlPathHelper urlPathHelper;

    public EmceeAuthenticationFilter() {
        this.urlPathHelper = new UrlPathHelper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isAuthenticated(request)) {
            String passwd = request.getParameter("passwd");
            if(!StringUtils.hasText(passwd)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "관계자외 출입금지!");
            } else {
                // TODO 암호 검증

                request.getSession().setAttribute(SESSION_AUTHENTICATION_KEY, true);

                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        String path = urlPathHelper.getLookupPathForRequest(request);
        return path.startsWith("/emcee") && request.getSession().getAttribute(SESSION_AUTHENTICATION_KEY) == null;
    }

}
