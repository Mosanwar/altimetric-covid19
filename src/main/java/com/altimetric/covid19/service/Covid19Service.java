package com.altimetric.covid19.service;

import com.altimetric.covid19.Model.DailyCovid19Record;
import com.altimetric.covid19.Model.MonthlyAvg;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface Covid19Service {
    ResponseEntity<List<MonthlyAvg>> calcAvg();

    List<MonthlyAvg> getMonthlyAvgList(Map<Integer, List<DailyCovid19Record>> monthlyRecords);

    List<DailyCovid19Record> getDailyRecords();

    Map<Integer, List<DailyCovid19Record>> getRecordsMap(List<DailyCovid19Record> records);
}
