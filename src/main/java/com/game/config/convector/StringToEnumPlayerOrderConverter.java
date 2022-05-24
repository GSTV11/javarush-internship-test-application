package com.game.config.convector;

import com.game.controller.PlayerOrder;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumPlayerOrderConverter implements Converter<String, PlayerOrder> {
    @Override
    public PlayerOrder convert(String source) {
        return PlayerOrder.getPlayerOrder(source);
    }
}
