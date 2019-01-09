package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class PetPhysicalInfo implements Domain{
	
	private int id;
	
	private String width;
	
	private String height;
	
	private String weight;
	
	private String createDate;

}
