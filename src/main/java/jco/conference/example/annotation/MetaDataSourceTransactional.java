package jco.conference.example.annotation;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Transactional("metaDataSource")
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaDataSourceTransactional {

    Propagation propagation() default Propagation.REQUIRED;
    Isolation isolation() default Isolation.DEFAULT;
    boolean readOnly() default false;

}
