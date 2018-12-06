package net.octacomm.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.exceptions.InvalidPasswordException;
import net.octacomm.sample.exceptions.NotFoundUserException;
import net.octacomm.sample.message.ResultMessage;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	UserMapper userMapper;

	@Override
	public ResultMessageGroup login(User user) throws NotFoundUserException, InvalidPasswordException{
		ResultMessageGroup group = null;
		if (userMapper.getUser(user) == null) {
			group = new ResultMessageGroup();
			group.setResultMessage(ResultMessage.NOT_FOUND_EMAIL);
			group.setDomain(null);
			return group;
		}
		User result = userMapper.getUserForAuth(user);
		if (result == null) {
			group = new ResultMessageGroup();
			group.setResultMessage(ResultMessage.ID_PASSWORD_DO_NOT_MATCH);
			group.setDomain(null);
			return group;
		}
		group = new ResultMessageGroup();
		group.setResultMessage(ResultMessage.SUCCESS);
		group.setDomain(result);
		return group;
	}

}
