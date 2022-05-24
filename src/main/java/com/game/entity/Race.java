package com.game.entity;

import java.util.Arrays;

public enum Race {
    HUMAN("ЧЕЛОВЕК"),
    DWARF("ГНОМ"),
    ELF("ЭЛЬФ"),
    GIANT("ГИГАНТ"),
    ORC("ОРК"),
    TROLL("ТРОЛЛЬ"),
    HOBBIT("ХОББИТ");

    private final String nameRus;

    Race(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getNameRus() {
        return nameRus;
    }

    public static Race getRace(String name) {
        boolean onlyLatinAlphabet = name.matches("^[a-zA-Z0-9]+$");
        return Arrays.stream(Race.values())
                .filter(race -> onlyLatinAlphabet
                        ? race.name().equals(name)
                        : race.getNameRus().equals(name))
                .findFirst()
                .orElse(null);
    }
}