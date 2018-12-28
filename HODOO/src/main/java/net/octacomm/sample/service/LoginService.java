package net.octacomm.sample.service;

import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.exceptions.InvalidPasswordException;
import net.octacomm.sample.exceptions.NotFoundUserException;

public interface LoginService {

	SessionMaintenance login(User user) throws NotFoundUserException, InvalidPasswordException;
	
	SessionMaintenance getAllInfoLogin(User user) throws NotFoundUserException, InvalidPasswordException;
	
	ResultMessageGroup login2(User user) throws NotFoundUserException, InvalidPasswordException;
}
