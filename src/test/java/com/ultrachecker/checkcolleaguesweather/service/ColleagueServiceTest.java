package com.ultrachecker.checkcolleaguesweather.service;

import com.ultrachecker.checkcolleaguesweather.client.WeatherClient;
import com.ultrachecker.checkcolleaguesweather.dao.ColleagueDao;
import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.Main;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.Weather;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.service.impl.ColleagueServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ColleagueServiceTest {

    @Mock
    private WeatherClient<WeatherClientDto> weatherClient;

    @Mock
    private WeatherService<WeatherDto, WeatherClientDto> weatherService;

    @Mock
    private ColleagueDao<ColleagueDto> colleagueDao;

    @InjectMocks
    private ColleagueServiceImpl colleagueService;

    @Test
    @DisplayName("Weather service should be call with given parameters")
    public void createSuccessTest() {
        Weather weather = new Weather();
        weather.setMain("Clouds");
        Main main = new Main();
        main.setTemp(283);
        WeatherClientDto weatherClientDto = new WeatherClientDto();
        Weather[] weathers = new Weather[1];
        weathers[0] = weather;
        weatherClientDto.setWeather(weathers);
        weatherClientDto.setMain(main);

        ColleagueDto colleagueDto = new ColleagueDto();
        colleagueDto.setColleagueId(1L);
        colleagueDto.setCity("Moscow");
        colleagueDto.setJobTitle("Developer");
        colleagueDto.setName("Artemy");
        colleagueDto.setBirthday(new Date());

        given(weatherClient.makeRequest("Moscow")).willReturn(weatherClientDto);
        given(colleagueDao.create(colleagueDto)).willReturn(1L);

        colleagueService.create(colleagueDto);

        verify(weatherService).create(weatherClientDto, 1L);
    }
}
