package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class BfiAnswer implements Domain {
	private int id;
    private String answer;
}
