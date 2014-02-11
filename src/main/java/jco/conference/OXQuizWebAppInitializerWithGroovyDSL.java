package jco.conference;

import jco.conference.oxquiz.security.AnonymousEntryAuthenticationFilter;
import jco.conference.oxquiz.security.EmceeAuthenticationFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

public class OXQuizWebAppInitializerWithGroovyDSL extends AbstractDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if("GroovyDSL".equals(servletContext.getInitParameter("configType"))) {
            super.onStartup(servletContext);
        }
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebGenricGroovyApplicationContext rootContext = new WebGenricGroovyApplicationContext();
        rootContext.setConfigLocation("classpath:META-INF/spring/repository-module.config");

        return rootContext;
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        WebGenricGroovyApplicationContext webContext = new WebGenricGroovyApplicationContext();
        webContext.setConfigLocation("classpath:META-INF/spring/web-module.config");

        return webContext;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Override
    protected String getServletName() {
        return OXQuizWebAppInitializerWithGroovyDSL.class.getSimpleName();
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{ new AnonymousEntryAuthenticationFilter(), new EmceeAuthenticationFilter() };
    }


    class WebGenricGroovyApplicationContext extends AbstractRefreshableWebApplicationContext {

        @Override
        protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
            GroovyBeanDefinitionReader beanDefinitionReader = new GroovyBeanDefinitionReader(beanFactory);

            beanDefinitionReader.setEnvironment(getEnvironment());
            beanDefinitionReader.setResourceLoader(this);

            loadBeanDefinitions(beanDefinitionReader);
        }

        protected void loadBeanDefinitions(GroovyBeanDefinitionReader reader) throws IOException {
            for (String configLocation : getConfigLocations()) {
                reader.loadBeanDefinitions(configLocation);
            }
        }

    }

}
