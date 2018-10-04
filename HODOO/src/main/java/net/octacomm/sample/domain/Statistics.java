package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Statistics implements Domain {

    private String theHour;

    private String theDay;

    private String theWeek;

    private String theMonth;
    
    private String theYear;

    private int average;
}
