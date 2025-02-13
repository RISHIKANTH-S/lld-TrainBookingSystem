package com.example.IRCTC.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {
    private String trainId;

    private String trainNo;

    private List<List<Integer>> seats;

    private Map<String, String> stationTimes;

    private List<String> stations;

    public Train(){}

    @Override
    public String toString() {
        return "Train{" +
                "trainId='" + trainId + '\'' +
                ", trainNo='" + trainNo + '\'' +
                ", seats=" + seats +
                ", stationTimes=" + stationTimes +
                ", stations=" + stations +
                '}';
    }

    public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> stations){
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = generateDefaultSeats();
        this.stationTimes = stationTimes;
        this.stations = stations;
    }
    private static List<List<Integer>> generateDefaultSeats() { // assuming every train have fixed 5*5
        List<List<Integer>> defaultSeats = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add(0); // 0 represents an unoccupied seat
            }
            defaultSeats.add(row);
        }
        return defaultSeats;
    }
    public List<String> getStations(){
        return stations;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats){
        this.seats = seats;
    }

    public String getTrainId(){
        return trainId;
    }

    public Map<String, String> getStationTimes(){
        return stationTimes;
    }

    public String getTrainNo(){
        return trainNo;
    }

    public void setTrainNo(String trainNo){
        this.trainNo = trainNo;
    }

    public void setTrainId(String trainId){
        this.trainId = trainId;
    }

    public void setStationTimes(Map<String, String> stationTimes){
        this.stationTimes = stationTimes;
    }

    public void setStations(List<String> stations){
        this.stations = stations;
    }

    public String getTrainInfo(){
        return String.format("Train ID: %s Train No: %s", trainId, trainNo);
    }
}
