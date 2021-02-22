package com.sx.enums;

import lombok.Getter;

/**
 * 枚举
 */
public enum CountryEnum {

    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"韩"),SIX(6,"魏");

    @Getter private Integer index;
    @Getter private String country;

    CountryEnum(Integer index, String country) {
        this.index = index;
        this.country = country;
    }

    public static CountryEnum getCountry(int index){
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if (index == countryEnum.getIndex()) {
                return countryEnum;
            }
        }
        return null;
    }

    private int getIndex() {
        return index;
    }

    public String getCountry() {
        return country;
    }
}
