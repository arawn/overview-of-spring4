package jco.conference.example.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnMissingBeanCondition.class)
public @interface ConditionalOnMissingBean {

    Class<?> value();

}
