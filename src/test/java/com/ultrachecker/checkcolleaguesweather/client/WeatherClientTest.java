package com.ultrachecker.checkcolleaguesweather.client;

import com.ultrachecker.checkcolleaguesweather.client.impl.WeatherClientImpl;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(properties = {"spring.profiles.active:prod", "scheduler.enabled=false"})
public class WeatherClientTest {

    @Autowired
    WeatherClientImpl weatherClient;

    @Test
    @DisplayName("WeatherClientDto should be not null")
    public void testClientMakeRequest() {
        WeatherClientDto weatherClientDto = weatherClient.makeRequest("Moscow");

        assertNotNull(weatherClientDto.getWeather()[0].getMain());
    }

    @Test
    @DisplayName("Weather client should throw custom exception")
    public void testClientCustomException() {
        Exception exception = assertThrows(OpenWeatherMapException.class,
                () -> weatherClient.makeRequest("Mosco"));

        String actualMessage = exception.getMessage();
        assert(actualMessage.contains("city not found"));
    }
}
