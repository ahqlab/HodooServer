package net.octacomm.sample.domain;


import lombok.Data;

@Data
public class Pet implements Domain {

	private int petIdx;
	
	private String petGroupCode;
	
	private int basic;
	
	private int disease;
	
	private int physical;
	
	private int weight;
	
	private int sltQst;
	
	private String createDate;
	
	private String fixWeight;

}
