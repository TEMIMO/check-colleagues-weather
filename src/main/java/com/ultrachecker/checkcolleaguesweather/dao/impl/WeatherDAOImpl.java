package com.ultrachecker.checkcolleaguesweather.dao.impl;

import com.ultrachecker.checkcolleaguesweather.dao.WeatherDao;
import com.ultrachecker.checkcolleaguesweather.dto.UpdateStatus;
import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import com.ultrachecker.checkcolleaguesweather.exception.WeatherNotFoundException;
import com.ultrachecker.checkcolleaguesweather.mapper.WeatherRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WeatherDAOImpl implements WeatherDao<WeatherDto> {

    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_WEATHER = "INSERT INTO public.cw_weather (i_temperature, s_description, d_updated, i_update_status, colleague_id) VALUES(?,?,?,?,?)";
    private static final String UPDATE_WEATHER = "UPDATE public.cw_weather SET i_temperature=?, s_description=?, d_updated=?, i_update_status=?, colleague_id=? WHERE weather_id=?";
    private static final String READ_WEATHER_BY_ID = "SELECT * from public.cw_weather WHERE weather_id=?";
    private static final String READ_WEATHER_BY_UPDATE_STATUS = "SELECT * from public.cw_weather WHERE i_update_status=?";
    private static final String READ_WEATHER_BY_COLLEAGUE = "SELECT * from public.cw_weather WHERE colleague_id=?";
    private static final String DELETE_WEATHER_BY_ID = "DELETE FROM public.cw_weather WHERE weather_id=?";
    private static final String DELETE_WEATHER_BY_COLLEAGUE = "DELETE FROM public.cw_weather WHERE colleague_id=?";
    private static final String COUNT_WEATHER = "SELECT count(*) FROM public.cw_weather";

    @Autowired
    WeatherDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(WeatherDto weather) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_WEATHER);
            ps.setLong(1, weather.getTemperature());
            ps.setString(2, weather.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(4, weather.getUpdateStatus());
            ps.setLong(5, weather.getColleagueId());
            return ps;
        });
    }

    public WeatherDto findById(Long id) {
        return jdbcTemplate.queryForObject(READ_WEATHER_BY_ID, new WeatherRowMapper(), id);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_WEATHER_BY_ID, id);
    }

    public void deleteByColleagueId(Long id) throws WeatherNotFoundException{
        int update = jdbcTemplate.update(DELETE_WEATHER_BY_COLLEAGUE, id);
        if (update == 0) {
            throw new WeatherNotFoundException();
        }
    }

    public void update(WeatherDto weather) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_WEATHER);
            ps.setLong(1, weather.getTemperature());
            ps.setString(2, weather.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(4, weather.getUpdateStatus());
            ps.setLong(5, weather.getColleagueId());
            ps.setLong(6, weather.getWeatherId());
            return ps;
        });
    }

    public List<WeatherDto> findByUpdateStatus(UpdateStatus updateStatus) {
        Long statusCode = updateStatus.getStatusCode();
        return jdbcTemplate.query(READ_WEATHER_BY_UPDATE_STATUS, new WeatherRowMapper(), statusCode);
    }

    public WeatherDto findByColleagueId(Long id) throws WeatherNotFoundException {
        WeatherDto weatherDto = null;
        try {
            weatherDto = jdbcTemplate.queryForObject(READ_WEATHER_BY_COLLEAGUE, new WeatherRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new WeatherNotFoundException();
            }
        }
        return weatherDto;
    }

    public Page<WeatherDto> findAll(Pageable page) {
        Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("colleague_id");

        List<WeatherDto> weatherDtos = jdbcTemplate.query("SELECT * FROM public.cw_weather ORDER BY " + order.getProperty() +
                        " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset(), new WeatherRowMapper());

        return new PageImpl<WeatherDto>(weatherDtos, page, count());
    }

    public int count() {
        return jdbcTemplate.queryForObject(COUNT_WEATHER, Integer.class);
    }
}
