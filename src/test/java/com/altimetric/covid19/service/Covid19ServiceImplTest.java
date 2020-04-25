package com.altimetric.covid19.service;

import static org.mockito.Mockito.when;

import com.altimetric.covid19.Model.DailyCovid19Record;
import com.altimetric.covid19.Model.MonthlyAvg;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Covid19ServiceImplTest {

    @Autowired
    private Covid19Service service;

    private List<DailyCovid19Record> dailyList = new ArrayList<>();
    private List<MonthlyAvg> avgs = new ArrayList<>();
    private Map<Integer, List<DailyCovid19Record>> recordsMap;
    @BeforeAll
    void setUp() {
        dailyList.add(new DailyCovid19Record("1/18", 4, 0, 4, 0));
        dailyList.add(new DailyCovid19Record("1/19", 5, 0, 5, 0));
        dailyList.add(new DailyCovid19Record("1/20", 6, 0, 6, 0));
        dailyList.add(new DailyCovid19Record("1/21", 3, 0, 3, 0));
        dailyList.add(new DailyCovid19Record("2/1", 4, 0, 7, 0));
        dailyList.add(new DailyCovid19Record("2/2", 4, 0, 8, 0));
        dailyList.add(new DailyCovid19Record("2/3", 4, 0, 8, 0));
        dailyList.add(new DailyCovid19Record("2/4", 4, 0, 9, 0));
        dailyList.add(new DailyCovid19Record("2/5", 4, 0, 10, 0));
        dailyList.add(new DailyCovid19Record("2/6", 4, 0, 11, 0));
        dailyList.add(new DailyCovid19Record("2/7", 4, 0, 12, 0));

        avgs.add(new MonthlyAvg("1", 50.0));
        avgs.add(new MonthlyAvg("2", 100.0));
        avgs.add(new MonthlyAvg("3", 150.0));
        avgs.add(new MonthlyAvg("4", 200.0));

//        when(service.getDailyRecords()).thenReturn(dailyList);
//        when(service.calcAvg()).thenReturn(avgs);
    }

    @Test
    void getMapRecords() {
        recordsMap = service.getRecordsMap(dailyList);
        assertEquals(2, recordsMap.size());
    }

    @Test
    void getMonthlyAvgTest(){
        assertEquals(2, service.getMonthlyAvgList(recordsMap).size());
        assertEquals(4, service.getMonthlyAvgList(recordsMap).get(0).getAvg());
        assertEquals(9, service.getMonthlyAvgList(recordsMap).get(1).getAvg());
    }


    @Test
    void calcAvgReturnedSize() {
        ResponseEntity<List<MonthlyAvg>> response= service.calcAvg();
        assertEquals(7, response.getBody().size());
    }

    @Test
    void AvgOfFirstMonth(){
        ResponseEntity<List<MonthlyAvg>> response= service.calcAvg();
        assertEquals(61, response.getBody().get(0).getAvg());
    }
}