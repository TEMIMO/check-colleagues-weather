package com.ultrachecker.checkcolleaguesweather.controller;

import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;

import static org.junit.Assert.assertThrows;

@SpringBootTest(properties = {"spring.profiles.active:prod", "scheduler.enabled=false"})
public class ColleagueControllerTest {

    @Autowired
    ColleagueController colleagueController;

    @Test
    public void colleagueControllerSuccess() {
        ColleagueDto colleagueDto = new ColleagueDto();
        colleagueDto.setName("Artemy");
        colleagueDto.setBirthday(new Date());
        colleagueDto.setCity("Moscow");
        colleagueDto.setJobTitle("Developer");

        String result = colleagueController.addColleague(colleagueDto);

        Assertions.assertEquals(result, "Colleague added!");
    }

    @Test
    public void colleagueControllerError() {
        ColleagueDto colleagueDto = new ColleagueDto();
        colleagueDto.setName("Artemyy");
        colleagueDto.setBirthday(new Date());
        colleagueDto.setCity("Moscow");
        colleagueDto.setJobTitle("Developerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        Exception exception = assertThrows(DataIntegrityViolationException.class,
                () -> colleagueController.addColleague(colleagueDto));

        String message = exception.getMessage();
        assert(message.contains("значение не умещается в тип"));
    }

}
