package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class PetGroupMapping implements Domain{
	
	private int id;
	
	private int groupId;
	
	private int petId;
	
	private int depth1;
	
	private int depth2;
	
	private int depth3;
	
	private int depth4;

}
