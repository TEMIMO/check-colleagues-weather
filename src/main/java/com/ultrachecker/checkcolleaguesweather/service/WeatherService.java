package com.ultrachecker.checkcolleaguesweather.service;

import com.ultrachecker.checkcolleaguesweather.dto.WeatherSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface WeatherService<T, D> {
    void create(D weatherClientDto, Long id);
    void deleteByColleagueId(Long id);
    void updateByColleagueId(Long id, String city);
    T getByColleagueId(Long id);
    WeatherSimpleDto getByCity(String city);
    Page<T> findAll(PageRequest pageRequest);
}
