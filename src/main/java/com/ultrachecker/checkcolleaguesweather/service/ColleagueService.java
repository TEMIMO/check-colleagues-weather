package com.ultrachecker.checkcolleaguesweather.service;

public interface ColleagueService<T> {
    void create(T colleagueDto);
    void update(Long id, T colleagueDto);
    T get(Long id);
    void delete(Long id);
}
