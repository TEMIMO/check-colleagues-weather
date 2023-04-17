package com.ultrachecker.checkcolleaguesweather.dao;

import com.ultrachecker.checkcolleaguesweather.dto.UpdateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WeatherDao<T> {
    void create(T t);
    T findById(Long id);
    int deleteById(Long id);
    void deleteByColleagueId(Long id);
    void update(T t);
    List<T> findByUpdateStatus(UpdateStatus updateStatus);
    T findByColleagueId(Long id);
    Page<T> findAll(Pageable pageable);

}
