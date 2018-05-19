package com.easy.fly.flyeasy.common;

public enum NewsCountry {

    //ae ar at au be bg br ca ch cn co cu cz de eg fr gb gr hk hu id ie il in it jp kr lt lv ma mx my ng nl no nz ph pl pt ro rs ru sa se sg si sk th tr tw ua us ve za

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
