package jco.conference;

import jco.conference.oxquiz.RepositoryConfig;
import jco.conference.oxquiz.WebConfig;
import jco.conference.oxquiz.WebSocketConfig;
import jco.conference.oxquiz.security.AnonymousEntryAuthenticationFilter;
import jco.conference.oxquiz.security.EmceeAuthenticationFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class OXQuizWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if("Java".equals(servletContext.getInitParameter("configType"))) {
            super.onStartup(servletContext);
        }
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RepositoryConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class, WebSocketConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Override
    protected String getServletName() {
        return OXQuizWebAppInitializer.class.getSimpleName();
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{ new AnonymousEntryAuthenticationFilter(), new EmceeAuthenticationFilter() };
    }

}