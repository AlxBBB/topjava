package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    private Set<Formatter<?>> formatters = new HashSet<Formatter<?>>();

    @PostConstruct
    public void init() {
        formatters.add(getLocalDateFormatter());
        formatters.add(getLocalTimeFormatter());
        setFormatters(formatters);
    }

    public Formatter<LocalDate> getLocalDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String s, Locale locale) throws ParseException {
                return StringUtils.isEmpty(s) ? null : LocalDate.parse(s);
            }

            @Override
            public String print(LocalDate localDate, Locale locale) {
                return localDate.toString();
            }
        };
    }

    public Formatter<LocalTime> getLocalTimeFormatter() {
        return new Formatter<LocalTime>() {
            @Override
            public LocalTime parse(String s, Locale locale) throws ParseException {
                return StringUtils.isEmpty(s) ? null : LocalTime.parse(s);
            }

            @Override
            public String print(LocalTime localTime, Locale locale) {
                return localTime.toString();
            }
        };
    }

}