package com.ultrachecker.checkcolleaguesweather.client;

import com.ultrachecker.checkcolleaguesweather.client.impl.WeatherClientImpl;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThrows;

@SpringBootTest(properties = {"spring.profiles.active:test", "scheduler.enabled=false"})
public class WeatherClientProfileTest {

    @Autowired
    WeatherClientImpl weatherClient;

    @Test
    @DisplayName("Weather client should throw custom exception")
    public void testClientCustomException() {
        Exception exception = assertThrows(OpenWeatherMapException.class,
                () -> weatherClient.makeRequest("Moscow"));

        String actualMessage = exception.getMessage();
        assert(actualMessage.contains("Invalid API key"));
    }
}
