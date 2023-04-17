package com.ultrachecker.checkcolleaguesweather.controller;

import com.ultrachecker.checkcolleaguesweather.dto.ColleagueDto;
import com.ultrachecker.checkcolleaguesweather.service.ColleagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ColleagueController {

    private final ColleagueService<ColleagueDto> colleagueService;

    @Autowired
    ColleagueController(ColleagueService<ColleagueDto> colleagueService) {
        this.colleagueService = colleagueService;
    }

    @PostMapping("/addColleague")
    public String addColleague(@RequestBody ColleagueDto colleagueDto) {
        colleagueService.create(colleagueDto);
        return "Colleague added!";
    }

    @GetMapping("/getColleague/{id}")
    public ColleagueDto getColleague(@PathVariable Long id) {
        return colleagueService.get(id);
    }

    @PostMapping("/updateColleague/{id}")
    public String updateColleague(@PathVariable Long id, @RequestBody ColleagueDto colleagueDto) {
        colleagueService.update(id, colleagueDto);
        return "Colleague updated!";
    }

    @GetMapping("/deleteColleague/{id}")
    public String deleteColleague(@PathVariable Long id) {
        colleagueService.delete(id);
        return "Colleague deleted!";
    }
}
