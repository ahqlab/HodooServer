package net.octacomm.sample.domain.google;

import java.util.List;

import lombok.Data;

@Data
public class Location {
	
	private PlusCode plus_code;
	
	List<Results> results;
}
