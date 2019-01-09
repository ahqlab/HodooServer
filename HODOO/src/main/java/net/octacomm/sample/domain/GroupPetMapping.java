package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class GroupPetMapping implements Domain{

	private int id;
	
	private String groupCode;
	
	private String petGroupCode;
	
	private String createDate;

}
