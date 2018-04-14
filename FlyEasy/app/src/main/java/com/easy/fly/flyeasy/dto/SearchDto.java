package com.easy.fly.flyeasy.dto;

import lombok.Data;

@Data
public class SearchDto {

    private String locationFrom;
    private String locationTo;
    private String date;
    private Boolean sortByPrice;
    private Boolean sortByRating;

    public SearchDto(String locationFrom,String locationTo,String date,Boolean sortByPrice,Boolean sortByRating){
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.date = date;
        this.sortByPrice = sortByPrice;
        this.sortByRating = sortByRating;
    }
}
