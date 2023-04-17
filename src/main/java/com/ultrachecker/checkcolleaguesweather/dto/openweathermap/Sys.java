package com.ultrachecker.checkcolleaguesweather.dto.openweathermap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sys {
    private long type;
    private long id;
    private String country;
    private long sunrise;
    private long sunset;
}
