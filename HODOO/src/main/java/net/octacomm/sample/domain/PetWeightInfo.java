package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class PetWeightInfo implements Domain{
	
	private int id;
	
	private int petId;
	
	private int bcs;
	
	private String createDate;

}