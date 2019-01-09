package net.octacomm.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetWeightInfo;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.exceptions.InvalidPasswordException;
import net.octacomm.sample.exceptions.NotFoundUserException;
import net.octacomm.sample.message.ResultMessage;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private PetMapper petMapper;
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;
	

	@Override
	public SessionMaintenance login(User user) throws NotFoundUserException, InvalidPasswordException{
		SessionMaintenance sessionMaintenance = null;
		User exisId = userMapper.getUser(user);
		if (exisId == null) {
			sessionMaintenance = new SessionMaintenance();
			sessionMaintenance.setResultMessage(ResultMessage.NOT_FOUND_EMAIL);
			return sessionMaintenance;
		}
		User result = userMapper.getUserForAuth(user);
		if (result == null) {
			sessionMaintenance = new SessionMaintenance();
			sessionMaintenance.setResultMessage(ResultMessage.ID_PASSWORD_DO_NOT_MATCH);
			return sessionMaintenance;
		}
		sessionMaintenance = new SessionMaintenance();
		sessionMaintenance.setResultMessage(ResultMessage.SUCCESS);
		sessionMaintenance.setUser(result);
		getAllInformation(sessionMaintenance, result.getGroupCode());
		return sessionMaintenance;
	}
	
	public SessionMaintenance getAllInformation(SessionMaintenance sessionMaintenance, String groupCode) {
		List<User> users = userMapper.getGroupMemner(groupCode);
		sessionMaintenance.setGroupUsers(users);
		List<PetAllInfos> allInfos = petMapper.aboutMyPetListForIos(groupCode);
		for (PetAllInfos petAllInfos : allInfos) {
			if(petAllInfos.getPetWeightInfo() != null) {
				petAllInfos.setPetWeightInfo(petWeightInfoMapper.get(petAllInfos.getPetWeightInfo().getId()));
			}else {
				petAllInfos.setPetWeightInfo(new PetWeightInfo());
			}
		}
		sessionMaintenance.setPetAllInfo(allInfos);
		List<Device> devices = deviceMapper.myAllDeviceList(groupCode);
		sessionMaintenance.setDevices(devices);
		return sessionMaintenance;
	}

	@Override
	public SessionMaintenance getAllInfoLogin(User user) throws NotFoundUserException, InvalidPasswordException {
		SessionMaintenance sessionMaintenance = null;
		sessionMaintenance = new SessionMaintenance();
		sessionMaintenance.setResultMessage(ResultMessage.SUCCESS);
		sessionMaintenance.setUser(user);
		getAllInformation(sessionMaintenance, user.getGroupCode());
		return sessionMaintenance;
	}

	@Override
	public CommonResponce<User> login2(User user) throws NotFoundUserException, InvalidPasswordException{
		CommonResponce<User> group = null;
		if (userMapper.getUser(user) == null) {
			group = new CommonResponce<User>();
			group.setResultMessage(ResultMessage.NOT_FOUND_EMAIL);
			group.setDomain(null);
			return group;
		}
		User result = userMapper.getUserForAuth(user);
		if (result == null) {
			group = new CommonResponce<User>();
			group.setResultMessage(ResultMessage.ID_PASSWORD_DO_NOT_MATCH);
			group.setDomain(null);
			return group;
		}
		group = new CommonResponce<User>();
		group.setResultMessage(ResultMessage.SUCCESS);
		group.setDomain(result);
		return group;
	}

}
