package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class InvitationRequest implements Domain {
	private int id;
	private int fromUserIdx;
	private int toUserIdx;
	private Object created;
}
