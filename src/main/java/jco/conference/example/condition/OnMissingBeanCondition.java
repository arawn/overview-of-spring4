package jco.conference.example.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;

public class OnMissingBeanCondition implements Condition {

    private static final String annotationType = ConditionalOnMissingBean.class.getName();

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(annotationType);

        Class<?> beanClass  = (Class<?>) attributes.get("value");
        String[] beanNames = context.getBeanFactory().getBeanNamesForType(beanClass);

        System.out.println("beanClass = " + beanClass.getName());
        System.out.println("beanNames = " + StringUtils.arrayToCommaDelimitedString(beanNames));

        return beanNames == null || beanNames.length == 0;
    }

}
