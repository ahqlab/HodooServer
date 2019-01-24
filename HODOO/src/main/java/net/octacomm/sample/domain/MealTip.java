package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class MealTip implements Domain{
	
	private int tipIdx;
	
	private String language;
	
	private String title;
	
	private String content;

}
