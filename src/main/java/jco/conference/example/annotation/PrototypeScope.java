package jco.conference.example.annotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrototypeScope {

    ScopedProxyMode proxyMode() default ScopedProxyMode.DEFAULT;

}
