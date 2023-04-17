package com.ultrachecker.checkcolleaguesweather.dao.impl;

import com.ultrachecker.checkcolleaguesweather.dao.ColleagueDao;
import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.exception.ColleagueNotFoundException;
import com.ultrachecker.checkcolleaguesweather.mapper.ColleagueRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Component
public class ColleagueDAOImpl implements ColleagueDao<ColleagueDto> {

    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_COLLEAGUE = "INSERT INTO public.cw_colleague (s_name, s_job_title, d_birthday, s_city) VALUES(?,?,?,?)";
    private static final String UPDATE_COLLEAGUE = "UPDATE public.cw_colleague SET s_name=?, s_job_title=?, d_birthday=?, s_city=? WHERE colleague_id=?";
    private static final String READ_COLLEAGUE_BY_ID = "SELECT * FROM public.cw_colleague WHERE colleague_id=?";
    private static final String DELETE_COLLEAGUE_BY_ID = "DELETE FROM public.cw_colleague WHERE colleague_id=?";

    @Autowired
    ColleagueDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(ColleagueDto colleague) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_COLLEAGUE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, colleague.getName());
            ps.setString(2, colleague.getJobTitle());
            ps.setDate(3, new Date(colleague.getBirthday().getTime()));
            ps.setString(4, colleague.getCity());
            return ps;
            }, keyHolder);

        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("colleague_id");
    }

    public void update(ColleagueDto colleague) throws ColleagueNotFoundException{
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_COLLEAGUE);
            ps.setString(1, colleague.getName());
            ps.setString(2, colleague.getJobTitle());
            ps.setDate(3, new Date(colleague.getBirthday().getTime()));
            ps.setString(4, colleague.getCity());
            ps.setLong(5, colleague.getColleagueId());
            return ps;
        });
        if (update == 0) {
            throw new ColleagueNotFoundException();
        }
    }

    public ColleagueDto findById(Long id) throws ColleagueNotFoundException{
        ColleagueDto colleague = null;
        try {
            colleague = jdbcTemplate.queryForObject(READ_COLLEAGUE_BY_ID, new ColleagueRowMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new ColleagueNotFoundException();
            }
        }
        return colleague;
    }

    public void deleteById(Long id) throws ColleagueNotFoundException{
        int update = jdbcTemplate.update(DELETE_COLLEAGUE_BY_ID, id);
        if (update == 0) {
            throw new ColleagueNotFoundException();
        }
    }
}
