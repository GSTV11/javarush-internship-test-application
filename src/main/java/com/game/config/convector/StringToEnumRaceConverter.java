package com.game.config.convector;

import com.game.entity.Race;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumRaceConverter implements Converter<String, Race> {
    @Override
    public Race convert(String source) {
        return Race.getRace(source);
    }
}
