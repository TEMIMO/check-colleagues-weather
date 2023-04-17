package com.ultrachecker.checkcolleaguesweather.dao;

public interface ColleagueDao<T> {
    Long create(T t);
    void update(T t);
    T findById(Long id);
    void deleteById(Long id);
}
