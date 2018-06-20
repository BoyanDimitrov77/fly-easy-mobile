package com.easy.fly.flyeasy.common;

public enum NewsCountry {

    AUSTRALIA("au"),
    BULGARIA("bg"),
    GERMANY("de"),
    FRANCE("fr"),
    GREECE("gr"),
    RUSSIAN("ru"),
    USA("us");

    private final String country;

    NewsCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }


}
