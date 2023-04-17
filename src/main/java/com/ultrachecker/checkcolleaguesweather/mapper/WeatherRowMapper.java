package com.ultrachecker.checkcolleaguesweather.mapper;

import com.ultrachecker.checkcolleaguesweather.dto.WeatherDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherRowMapper implements RowMapper<WeatherDto> {
    @Override
    public WeatherDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        WeatherDto weatherDto = new WeatherDto();

        weatherDto.setWeatherId(rs.getLong("weather_id"));
        weatherDto.setTemperature(rs.getLong("i_temperature"));
        weatherDto.setDescription(rs.getString("s_description"));
        weatherDto.setUpdated(rs.getDate("d_updated"));
        weatherDto.setUpdateStatus(rs.getLong("i_update_status"));
        weatherDto.setColleagueId(rs.getLong("colleague_id"));

        return weatherDto;
    }
}
