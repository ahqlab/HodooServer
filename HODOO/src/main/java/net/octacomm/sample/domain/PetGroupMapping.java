package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class PetGroupMapping implements Domain{
	
	private int id;
	
	private int groupId;
	
	private int petId;

}
