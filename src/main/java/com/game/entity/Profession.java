package com.game.entity;

import java.util.Arrays;

public enum Profession {
    WARRIOR("ВОИН"),
    ROGUE("НЕГОДЯЙ"),
    SORCERER("КОЛДУН"),
    CLERIC("КЛЕРИК"),
    PALADIN("ПАЛАДИН"),
    NAZGUL("НАЗГУЛ"),
    WARLOCK("ЧЕРНОКНИЖНИК"),
    DRUID("ДРУИД");

    private final String nameRus;

    Profession(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getNameRus() {
        return nameRus;
    }

    public static Profession getProfession(String name) {
        boolean onlyLatinAlphabet = name.matches("^[a-zA-Z0-9]+$");
        return Arrays.stream(Profession.values())
                .filter(profession -> onlyLatinAlphabet
                        ? profession.name().equals(name)
                        : profession.getNameRus().equals(name))
                .findFirst()
                .orElse(null);
    }
}
