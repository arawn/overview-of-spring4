package jco.conference.jsr310;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JSR310SupportTest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime localDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime localTime;

    private ConversionService conversionService;

    @Before
    public void setup() {
        this.localDateTime = LocalDateTime.now();
        this.localDate = LocalDate.now();
        this.localTime = LocalTime.now();
        this.conversionService = new DefaultFormattingConversionService();
    }

    @Test
    public void testJSR310() {
        TypeDescriptor targetType = TypeDescriptor.valueOf(String.class);

        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(this);


        TypeDescriptor sourceTypeForLocalDateTime = propertyAccessor.getPropertyTypeDescriptor("localDateTime");
        System.out.println(conversionService.convert(localDateTime, sourceTypeForLocalDateTime, targetType));

        TypeDescriptor sourceTypeForLocalDate = propertyAccessor.getPropertyTypeDescriptor("localDate");
        System.out.println(conversionService.convert(localDate, sourceTypeForLocalDate, targetType));

        TypeDescriptor sourceTypeForLocalTime = propertyAccessor.getPropertyTypeDescriptor("localTime");
        System.out.println(conversionService.convert(localTime, sourceTypeForLocalTime, targetType));
    }

}
