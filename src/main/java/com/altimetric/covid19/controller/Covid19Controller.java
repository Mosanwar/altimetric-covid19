package com.altimetric.covid19.controller;

import com.altimetric.covid19.Model.MonthlyAvg;
import com.altimetric.covid19.service.Covid19Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Covid19Controller {

    @Autowired
    private Covid19Service service;

    @GetMapping("calc")
    public ResponseEntity<List<MonthlyAvg>> calcCovidAvg(){
        return service.calcAvg();
    }


    //--------------------------------setters and getters--------------------------

    public Covid19Service getService() {
        return service;
    }

    public void setService(Covid19Service service) {
        this.service = service;
    }
}
