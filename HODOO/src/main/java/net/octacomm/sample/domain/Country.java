package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class Country implements Domain {
	private int id;
	private String name;
}
