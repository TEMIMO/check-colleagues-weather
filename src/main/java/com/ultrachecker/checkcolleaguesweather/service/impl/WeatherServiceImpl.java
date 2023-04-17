package com.ultrachecker.checkcolleaguesweather.service.impl;

import com.ultrachecker.checkcolleaguesweather.client.WeatherClient;
import com.ultrachecker.checkcolleaguesweather.dao.WeatherDao;
import com.ultrachecker.checkcolleaguesweather.dto.UpdateStatus;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherSimpleDto;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import com.ultrachecker.checkcolleaguesweather.exception.WeatherNotFoundException;
import com.ultrachecker.checkcolleaguesweather.service.WeatherService;
import com.ultrachecker.checkcolleaguesweather.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class WeatherServiceImpl implements WeatherService<WeatherDto, WeatherClientDto> {

    private final WeatherDao<WeatherDto> weatherDAO;
    private final WeatherClient<WeatherClientDto> weatherClient;

    private static final double KELVIN_CONSTANT = 273.15;

    @Autowired
    WeatherServiceImpl(WeatherDao<WeatherDto> weatherDAO, WeatherClient<WeatherClientDto> weatherClient) {
        this.weatherDAO = weatherDAO;
        this.weatherClient = weatherClient;
    }

    public void create(WeatherClientDto weatherClientDto, Long colleagueId) {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setColleagueId(colleagueId);
        weatherDto.setDescription(weatherClientDto.getWeather()[0].getMain());
        weatherDto.setUpdated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        weatherDto.setUpdateStatus(UpdateStatus.SUCCESS.getStatusCode());
        weatherDto.setTemperature((long) (weatherClientDto.getMain().getTemp()-KELVIN_CONSTANT));

        weatherDAO.create(weatherDto);
    }

    public void deleteByColleagueId(Long id) throws WeatherNotFoundException{
        weatherDAO.deleteByColleagueId(id);
    }

    @Transactional
    public void updateByColleagueId(Long id, String city) throws OpenWeatherMapException, WeatherNotFoundException {
        WeatherClientDto weatherClientDto = weatherClient.makeRequest(city);

        WeatherDto weatherDto = weatherDAO.findByColleagueId(id);
        weatherDto.setDescription(weatherClientDto.getWeather()[0].getMain());
        weatherDto.setUpdated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        weatherDto.setUpdateStatus(UpdateStatus.SUCCESS.getStatusCode());
        weatherDto.setTemperature((long) (weatherClientDto.getMain().getTemp()-KELVIN_CONSTANT));

        weatherDAO.update(weatherDto);
    }

    public WeatherDto getByColleagueId(Long id) throws WeatherNotFoundException{
        return weatherDAO.findByColleagueId(id);
    }

    public WeatherSimpleDto getByCity(String city) throws OpenWeatherMapException{
        WeatherClientDto weatherClientDto = weatherClient.makeRequest(city);
        WeatherSimpleDto weatherSimpleDto = Converter.convertWeatherApiToDto(weatherClientDto);
        weatherSimpleDto.setCity(city);
        return weatherSimpleDto;
    }

    public Page<WeatherDto> findAll(PageRequest pageable) {
        return weatherDAO.findAll(pageable);
    }
}
