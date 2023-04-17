package com.ultrachecker.checkcolleaguesweather.client.impl;

import com.google.gson.Gson;
import com.ultrachecker.checkcolleaguesweather.client.WeatherClient;
import com.ultrachecker.checkcolleaguesweather.dto.openweathermap.WeatherClientDto;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class WeatherClientImpl implements WeatherClient<WeatherClientDto> {

    @Value("${spring.api.key}")
    private String API_KEY;

    @SneakyThrows
    public WeatherClientDto makeRequest(String city) throws OpenWeatherMapException {
        WeatherClientDto weatherClientDto;

        StringBuilder result = new StringBuilder();
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=" + API_KEY;

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
        } else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            throw new OpenWeatherMapException(result.toString());
        }
        Gson gson = new Gson();
        weatherClientDto = gson.fromJson(String.valueOf(result), WeatherClientDto.class);

        return weatherClientDto;
    }
}
