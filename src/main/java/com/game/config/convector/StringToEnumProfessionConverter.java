package com.game.config.convector;

import com.game.entity.Profession;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumProfessionConverter implements Converter<String, Profession> {
    @Override
    public Profession convert(String source) {
        return Profession.getProfession(source);
    }
}
