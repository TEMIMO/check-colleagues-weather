package com.ultrachecker.checkcolleaguesweather.scheduler;

import com.ultrachecker.checkcolleaguesweather.client.WeatherClient;
import com.ultrachecker.checkcolleaguesweather.dao.ColleagueDao;
import com.ultrachecker.checkcolleaguesweather.dao.WeatherDao;
import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.dto.UpdateStatus;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UpdateWeatherScheduler {

    private final WeatherClient<WeatherClientDto> weatherClient;
    private final WeatherDao<WeatherDto> weatherDAO;
    private final ColleagueDao<ColleagueDto> colleagueDAO;

    private static final double KELVIN_CONSTANT = 273.15;

    @Autowired
    UpdateWeatherScheduler(WeatherClient<WeatherClientDto> weatherClient, WeatherDao<WeatherDto> weatherDAO, ColleagueDao<ColleagueDto> colleagueDAO) {
        this.weatherClient = weatherClient;
        this.weatherDAO = weatherDAO;
        this.colleagueDAO = colleagueDAO;
    }

    @Scheduled(fixedRateString = "${spring.scheduler.fixed-rate}")
    public void updateWeather() {
        List<WeatherDto> weatherDtoList = weatherDAO.findByUpdateStatus(UpdateStatus.ERROR);
        weatherDtoList.addAll(weatherDAO.findByUpdateStatus(UpdateStatus.SUCCESS));

        weatherDtoList.forEach(weatherDto -> {
            ColleagueDto colleague = colleagueDAO.findById(weatherDto.getColleagueId());
            String city = colleague.getCity();
            WeatherClientDto weatherClientDto;
            try {
                weatherClientDto = weatherClient.makeRequest(city);
                weatherDto.setTemperature(Double.valueOf(weatherClientDto.getMain().getTemp()-KELVIN_CONSTANT).longValue());
                weatherDto.setDescription(weatherClientDto.getWeather()[0].getMain());
                weatherDto.setUpdateStatus(UpdateStatus.SUCCESS.getStatusCode());
                weatherDAO.update(weatherDto);
                System.out.println("Updated! " + LocalDateTime.now());
            } catch (OpenWeatherMapException exception) {
                weatherDto.setUpdateStatus(UpdateStatus.ERROR.getStatusCode());
                weatherDAO.update(weatherDto);
                System.out.println("Not updated! " + LocalDateTime.now());
            }
        });
    }
}
