package com.ultrachecker.checkcolleaguesweather.dto.openweathermap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {
    private double speed;
    private long deg;
    private double gust;
}
