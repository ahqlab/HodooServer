package net.octacomm.sample.service;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
	
	/*@Autowired
	UserMapper userMapper;

	@Override
	public boolean login(User user) throws NotFoundUserException, InvalidPasswordException {
		if (userMapper.getUser(user) == null) {
			throw new NotFoundUserException();
		}
		
		if (userMapper.getUserForAuth(user) == null) {
			throw new InvalidPasswordException();
		}
		return true;
	}*/

}
