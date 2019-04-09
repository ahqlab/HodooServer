package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.SessionMaintenance;
import net.octacomm.sample.domain.User;


@RequestMapping("/android/session")
@Controller
public class SessionMaintenanceControllerForAndroid {
	
	@Autowired
	private PetMapper petMapper;
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@ResponseBody
	@RequestMapping(value = "/get.do")
	public SessionMaintenance get(@RequestParam("groupCode") String groupCode) {
		SessionMaintenance maintenance = new SessionMaintenance();
		List<User> users = userMapper.getGroupMemner(groupCode);
		maintenance.setUser(users.get(0));
		maintenance.setGroupUsers(users);
		List<PetAllInfos> allInfos = petMapper.aboutMyPetList(groupCode);
		maintenance.setPetAllInfo(allInfos);
		List<Device> devices = deviceMapper.myAllDeviceList(groupCode);
		maintenance.setDevices(devices);
		return maintenance;
	}
}
