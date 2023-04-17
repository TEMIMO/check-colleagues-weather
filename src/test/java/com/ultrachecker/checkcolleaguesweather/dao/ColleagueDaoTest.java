package com.ultrachecker.checkcolleaguesweather.dao;

import com.ultrachecker.checkcolleaguesweather.dao.impl.ColleagueDAOImpl;
import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.exception.ColleagueNotFoundException;
import com.ultrachecker.checkcolleaguesweather.mapper.ColleagueRowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ColleagueDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    ColleagueDAOImpl colleagueDAO;

    @Test
    @DisplayName("Colleague dao should return given id if success")
    public void findByIdSuccessTest() {
        Long colleagueId = 1L;
        ColleagueDto colleagueDto = new ColleagueDto();
        colleagueDto.setColleagueId(colleagueId);
        colleagueDto.setCity("Moscow");
        colleagueDto.setJobTitle("Developer");
        colleagueDto.setName("Artemy");
        colleagueDto.setBirthday(new Date());

        Mockito.when(jdbcTemplate.queryForObject(anyString(), any(ColleagueRowMapper.class), anyLong())).thenReturn(colleagueDto);
        ColleagueDto byId = colleagueDAO.findById(colleagueId);

        assertEquals(byId, colleagueDto);
    }

    @Test
    @DisplayName("Colleague dao should throw exception if cannot find id")
    public void findByIdSuccessError() {
        Long colleagueId = 1L;
        ColleagueDto colleagueDto = new ColleagueDto();
        colleagueDto.setColleagueId(colleagueId);
        colleagueDto.setCity("Moscow");
        colleagueDto.setJobTitle("Developer");
        colleagueDto.setName("Artemy");
        colleagueDto.setBirthday(new Date());

        Mockito.when(jdbcTemplate.queryForObject(anyString(), any(ColleagueRowMapper.class), anyLong())).thenThrow(IncorrectResultSizeDataAccessException.class);

        Exception exception = assertThrows(ColleagueNotFoundException.class,
                () -> colleagueDAO.findById(colleagueId));

        assertEquals(exception.getClass(), ColleagueNotFoundException.class);
    }
}
