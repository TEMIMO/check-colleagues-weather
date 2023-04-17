package com.ultrachecker.checkcolleaguesweather.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WeatherDto {
    private Long weatherId;
    private Long temperature;
    private String description;
    private Date updated;
    private Long updateStatus;
    private Long colleagueId;
}
