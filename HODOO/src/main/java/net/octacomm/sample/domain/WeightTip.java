package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class WeightTip implements Domain{
	
	private int tipIdx;
	
	private String language;
	
	private int bcs;
	
	private String title;
	
	private String content;

}
