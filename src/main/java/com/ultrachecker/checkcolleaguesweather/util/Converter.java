package com.ultrachecker.checkcolleaguesweather.util;

import com.ultrachecker.checkcolleaguesweather.dto.WeatherSimpleDto;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;

public class Converter {

    private static final double KELVIN_CONSTANT = 273.15;

    public static WeatherSimpleDto convertWeatherApiToDto(WeatherClientDto weatherClientDto) {
        WeatherSimpleDto weatherSimpleDto = new WeatherSimpleDto();
        weatherSimpleDto.setDescription(weatherClientDto.getWeather()[0].getMain());
        weatherSimpleDto.setTemperature((long) (weatherClientDto.getMain().getTemp() - KELVIN_CONSTANT));
        return weatherSimpleDto;
    }
}
