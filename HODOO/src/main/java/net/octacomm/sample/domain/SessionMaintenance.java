package net.octacomm.sample.domain;

import java.util.List;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;

@Data
public class SessionMaintenance implements Domain{
	
	User user;
	
	List<User> groupUsers;
	
	List<Device> devices;
	
	List<PetAllInfos> petAllInfo;
	
	public ResultMessage resultMessage;
	
}
