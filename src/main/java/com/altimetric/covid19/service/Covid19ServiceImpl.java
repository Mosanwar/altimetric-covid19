package com.altimetric.covid19.service;

import com.altimetric.covid19.Model.MonthlyAvg;
import com.altimetric.covid19.Model.DailyCovid19Record;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Covid19ServiceImpl implements Covid19Service {
    String dailyRecordsUrl = "https://covidtracking.com/api/cdc/daily";

    @Override
    public ResponseEntity<List<MonthlyAvg>> calcAvg(){
        try {
            List<DailyCovid19Record> recordsList = getDailyRecords();
            Map<Integer, List<DailyCovid19Record>> monthlyRecords = getRecordsMap(recordsList);
            List<MonthlyAvg> monthlyAvgList = getMonthlyAvgList(monthlyRecords);
            return new ResponseEntity<List<MonthlyAvg>>(monthlyAvgList, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<List<MonthlyAvg>>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<MonthlyAvg> getMonthlyAvgList(Map<Integer, List<DailyCovid19Record>> monthlyRecords) {
        List<MonthlyAvg> list = new ArrayList<>();
        for (Map.Entry<Integer, List<DailyCovid19Record>> entry: monthlyRecords.entrySet()) {
            if (entry.getKey() != 4)
                addAvgOfMonth(entry, list);
            else {
                addAvgOdLastMonth(entry, list);
            }
        }
        return list;
    }

    public void addAvgOdLastMonth(Map.Entry<Integer, List<DailyCovid19Record>> entry, List<MonthlyAvg> list) {
        int totalCases = 0;
        for (DailyCovid19Record record: entry.getValue()) {
            if (record.getDailyTotal() != 0)
                totalCases += record.getDailyTotal();
        }

        double currentAvg = totalCases/entry.getValue().size();
        list.add(new MonthlyAvg(entry.getKey()+"", currentAvg));

//        double apr10xRate = ((30-entry.getValue().size())*currentAvg)/(30-entry.getValue().size());
        double apr10xRate_acutal = (int)(totalCases+(currentAvg*(30-entry.getValue().size())))/30;

        list.add(new MonthlyAvg("4_10xRate", apr10xRate_acutal));
        list.add(new MonthlyAvg("5", currentAvg));
        list.add(new MonthlyAvg("5_10xRate", currentAvg*10));
    }

    public void addAvgOfMonth(Map.Entry<Integer, List<DailyCovid19Record>> entry, List<MonthlyAvg> list) {
        MonthlyAvg monthlyAvg = new MonthlyAvg();
        monthlyAvg.setMonth(entry.getKey()+"");
        int totalCases = 0;
        for (DailyCovid19Record record: entry.getValue()) {
            totalCases += record.getDailyTotal();
        }
        monthlyAvg.setAvg((double) (totalCases/entry.getValue().size()));
        list.add(monthlyAvg);
    }

    @Override
    public List<DailyCovid19Record> getDailyRecords() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<DailyCovid19Record>> responseEntity = restTemplate.exchange(
                dailyRecordsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DailyCovid19Record>>() {}
        );
        List<DailyCovid19Record> records = responseEntity.getBody();
        return records;
    }

    @Override
    public Map<Integer, List<DailyCovid19Record>> getRecordsMap(List<DailyCovid19Record> records){
        Map<Integer, List<DailyCovid19Record>> map = new HashMap<>();
        for (DailyCovid19Record record: records) {
            int month = extractMonth(record);
            if(map.containsKey(month)){
                map.get(month).add(record);
            } else {
                List<DailyCovid19Record> list = new ArrayList<>();
                list.add(record);
                map.put(month, list);
            }
        }
        return map;
    }

    public int extractMonth(DailyCovid19Record record) {
        if (record.getDateCollected().charAt(0) == '0')
            return Integer.parseInt(record.getDateCollected().charAt(1)+"");
        return Integer.parseInt(record.getDateCollected().charAt(0)+"");
    }
}
