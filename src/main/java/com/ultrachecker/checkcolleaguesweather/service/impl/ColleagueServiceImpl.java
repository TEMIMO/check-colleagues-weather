package com.ultrachecker.checkcolleaguesweather.service.impl;

import com.ultrachecker.checkcolleaguesweather.client.WeatherClient;
import com.ultrachecker.checkcolleaguesweather.dao.ColleagueDao;
import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.exception.ColleagueNotFoundException;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import com.ultrachecker.checkcolleaguesweather.exception.WeatherNotFoundException;
import com.ultrachecker.checkcolleaguesweather.service.ColleagueService;
import com.ultrachecker.checkcolleaguesweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColleagueServiceImpl implements ColleagueService<ColleagueDto> {

    private final ColleagueDao<ColleagueDto> colleagueDAO;
    private final WeatherClient<WeatherClientDto> weatherClient;
    private final WeatherService<WeatherDto, WeatherClientDto> weatherService;

    @Autowired
    ColleagueServiceImpl(ColleagueDao<ColleagueDto> colleagueDAO, WeatherClient<WeatherClientDto> weatherClient, WeatherService<WeatherDto, WeatherClientDto> weatherService) {
        this.colleagueDAO = colleagueDAO;
        this.weatherClient = weatherClient;
        this.weatherService = weatherService;
    }

    @Transactional
    public void create(ColleagueDto colleagueDto) throws OpenWeatherMapException {
        WeatherClientDto weatherClientDto = weatherClient.makeRequest(colleagueDto.getCity());
        Long colleagueId = colleagueDAO.create(colleagueDto);
        weatherService.create(weatherClientDto, colleagueId);
    }

    @Transactional
    public void update(Long id, ColleagueDto colleagueDto) throws OpenWeatherMapException, WeatherNotFoundException, ColleagueNotFoundException {
        colleagueDto.setColleagueId(id);
        colleagueDAO.update(colleagueDto);
        weatherService.updateByColleagueId(id, colleagueDto.getCity());
    }

    public ColleagueDto get(Long id) throws ColleagueNotFoundException {
        return colleagueDAO.findById(id);
    }

    @Transactional
    public void delete(Long id) throws ColleagueNotFoundException, WeatherNotFoundException {
        weatherService.deleteByColleagueId(id);
        colleagueDAO.deleteById(id);
    }
}
