package com.ultrachecker.checkcolleaguesweather.mapper;

import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColleagueRowMapper implements RowMapper<ColleagueDto> {
    @Override
    public ColleagueDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ColleagueDto colleagueDto = new ColleagueDto();

        colleagueDto.setName(rs.getString("s_name"));
        colleagueDto.setJobTitle(rs.getString("s_job_title"));
        colleagueDto.setBirthday(rs.getDate("d_birthday"));
        colleagueDto.setCity(rs.getString("s_city"));
        colleagueDto.setColleagueId(rs.getLong("colleague_id"));

        return colleagueDto;
    }
}
