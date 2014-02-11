package jco.conference;

import jco.conference.example.ExampleWebConfig;
import jco.conference.example.ExampleWebSocketConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ExampleWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ExampleWebConfig.class, ExampleWebSocketConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/example/*" };
    }

    @Override
    protected String getServletName() {
        return ExampleWebAppInitializer.class.getSimpleName();
    }

}