package net.octacomm.sample.service;

import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.User;

public interface UserService {
	
	User createUser(User user);
	
	int saveFCMToken( User user );
}
