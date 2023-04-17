package com.ultrachecker.checkcolleaguesweather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherSimpleDto {
    private String city;
    private Long temperature;
    private String description;
}
