package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class IntegerFormatter implements Formatter<Integer> {
    @Override
    public Integer parse(String s, Locale locale) throws ParseException {
        if ("".equals(s)) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    @Override
    public String print(Integer integer, Locale locale) {
        return Integer.toString(integer);
    }
}
