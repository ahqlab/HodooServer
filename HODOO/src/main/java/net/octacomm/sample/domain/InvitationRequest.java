package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class InvitationRequest implements Domain {
	private int id;
    private int toUserIdx;
    private int fromUserIdx;
    private int state;
    private Object created;
    private String nickname;
}
