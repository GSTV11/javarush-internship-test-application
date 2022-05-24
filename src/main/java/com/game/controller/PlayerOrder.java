package com.game.controller;

import java.util.Arrays;

public enum PlayerOrder {
    ID("id", "ИДЕНТИФИКАТОР"), // default
    NAME("name", "ИМЯ"),
    EXPERIENCE("experience", "ОПЫТ"),
    BIRTHDAY("birthday", "ДЕНЬ РОЖДЕНИЯ"),
    LEVEL("level", "УРОВЕНЬ");

    private final String fieldName;

    private final String fieldNameRus;

    PlayerOrder(String fieldName, String fieldNameRus) {
        this.fieldName = fieldName;
        this.fieldNameRus = fieldNameRus;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldNameRus() {
        return fieldNameRus;
    }

    public static PlayerOrder getPlayerOrder(String fieldName) {
        boolean onlyLatinAlphabet = fieldName.matches("^[a-zA-Z0-9]+$");
        return Arrays.stream(PlayerOrder.values())
                .filter(playerOrder -> onlyLatinAlphabet
                        ? playerOrder.name().equals(fieldName)
                        : playerOrder.getFieldNameRus().equals(fieldName))
                .findFirst()
                .orElse(PlayerOrder.ID);
    }
}