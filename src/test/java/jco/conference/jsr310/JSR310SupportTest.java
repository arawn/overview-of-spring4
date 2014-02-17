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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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
    public void testJSR310API() {
        LocalDate 오늘 = LocalDate.now();
        LocalDate 삼일절  = LocalDate.of(2014, 3, 1);
        LocalDate 현충일 = LocalDate.parse("2014-06-06");

        LocalDate 어제 = 오늘.minusDays(1);
        LocalDate 내일 = 오늘.plusDays(1);
        LocalDate 일주일뒤 = 오늘.plusWeeks(1);


        DateTimeFormatter style = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        String styleFormat = 오늘.format(style);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String patternFormat = 오늘.format(pattern);
    }

    @Test
    public void testJSR310Converter() {
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
