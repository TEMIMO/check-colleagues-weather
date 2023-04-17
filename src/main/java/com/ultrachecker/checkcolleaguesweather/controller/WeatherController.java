package com.ultrachecker.checkcolleaguesweather.controller;

import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherSimpleDto;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import com.ultrachecker.checkcolleaguesweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    public final WeatherService<WeatherDto, WeatherClientDto> weatherService;

    @Autowired
    WeatherController(WeatherService<WeatherDto, WeatherClientDto> weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("getWeatherByColleague/{id}")
    public WeatherDto getByColleagueId(@PathVariable Long id) {
        return weatherService.getByColleagueId(id);
    }

    @GetMapping("getWeatherList")
    public Page<WeatherDto> getWeatherList(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return weatherService.findAll(PageRequest.of(offset, limit));
    }

    @GetMapping("getWeatherByCity/{city}")
    public WeatherSimpleDto getByCity(@PathVariable String city) throws OpenWeatherMapException {
        return weatherService.getByCity(city);
    }
}
