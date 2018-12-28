package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class RealTimeBedData implements Domain{

	private int id;
	
	private String mac;
	
	private float weight;
	
	private float temp;
	
	private String createDate;

}