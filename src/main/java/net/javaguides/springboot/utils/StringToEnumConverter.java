package net.javaguides.springboot.utils;

import net.javaguides.springboot.enums.BackgroundColor;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, BackgroundColor> {
    @Override
    public BackgroundColor convert(String source) {
        try {
            return BackgroundColor.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}