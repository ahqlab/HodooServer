package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Statistics implements Domain {
	
	public Statistics() {
	}
	
    public Statistics(String theWeek, float average) {
    	this.theWeek = theWeek;
    	this.average = average;
	}

	private String theHour;

    private String theDay;

    private String theWeek;

    private String theMonth;
    
    private String theYear;
    
    private String today;

    private float average;
}
