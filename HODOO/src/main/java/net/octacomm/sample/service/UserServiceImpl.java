package net.octacomm.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.octacomm.sample.dao.mapper.GroupsMapper;
import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.utils.MathUtil;

@Service
public class UserServiceImpl implements UserService {

	public static final int SUCCESS = 1;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserGroupMappingMapper userGroupMapper;

	@Override
	public User createUser(User user) {
		if (userMapper.insert(user) == SUCCESS) {
			return user;
		} else {
			return null;
		}
	}

	@Override
	public int saveFCMToken(User user) {
		// TODO Auto-generated method stub
		return userMapper.saveFCMToken(user);
	}

}
