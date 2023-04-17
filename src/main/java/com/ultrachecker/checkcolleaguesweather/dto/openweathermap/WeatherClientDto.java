package com.ultrachecker.checkcolleaguesweather.dto.openweathermap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherClientDto {
    //private Coord coord;
    private Weather[] weather;
    //private String base;
    private Main main;
    //private Long visibility;
    //private Wind wind;
    //private Clouds clouds;
    //private Long dt;
    //private Sys sys;
    //private Long timezone;
    //private Long id;
    //private String name;
    //private Long code;
}
