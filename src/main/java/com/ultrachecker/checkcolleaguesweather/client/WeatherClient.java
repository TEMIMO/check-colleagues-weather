package com.ultrachecker.checkcolleaguesweather.client;

public interface WeatherClient<T> {
    T makeRequest(String city);
}
