package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class MealHistory implements Domain {

	private int historyIdx;

	private String groupId;

	private int userIdx;

	private int feedIdx;
	
	private int petIdx;
	
	private float calorie;
	
	private int unitIndex;
	
	private String unitString;
	
	
	private String createDate;

}
