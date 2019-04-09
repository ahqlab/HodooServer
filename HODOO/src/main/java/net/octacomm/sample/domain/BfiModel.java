package net.octacomm.sample.domain;

import java.util.List;

import lombok.Data;

@Data
public class BfiModel implements Domain {
	private int id;
    private String question;
    private String answerIds;
    private List<BfiAnswer> answers;
    private int additionalId;
}
