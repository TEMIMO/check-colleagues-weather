package com.ultrachecker.checkcolleaguesweather.controller;

import com.ultrachecker.checkcolleaguesweather.exception.ColleagueNotFoundException;
import com.ultrachecker.checkcolleaguesweather.exception.ExceptionDetails;
import com.ultrachecker.checkcolleaguesweather.exception.OpenWeatherMapException;
import com.ultrachecker.checkcolleaguesweather.exception.WeatherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ColleagueNotFoundException.class)
    public ResponseEntity<ExceptionDetails> colleagueNotFoundExceptionHandler() {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setMessage("Can not find colleague with this id");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDetails);
    }

    @ExceptionHandler(OpenWeatherMapException.class)
    public ResponseEntity<ExceptionDetails> openWeatherMapExceptionHandler(OpenWeatherMapException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setMessage(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDetails); //TODO
    }

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<ExceptionDetails> weatherNotFoundExceptionHandler() {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setMessage("Cannot find weather with this colleague id");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDetails);
    }
}
