package com.ultrachecker.checkcolleaguesweather.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ColleagueDto {
    private String name;
    private String jobTitle;
    private Date birthday;
    private String city;
    private Long colleagueId;
}
