package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.GroupPetMappingMapper;
import net.octacomm.sample.domain.ArrayListDevice;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/device")
@Controller
public class DeviceController {
	
	@Autowired private DeviceMapper deviceNapper;
	@Autowired private GroupPetMappingMapper groupPetMappingMapper;

	@ResponseBody
	@RequestMapping(value = "/my/device/list", method = RequestMethod.POST)
	public List<Device> myDeviceList(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceList(groupCode);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/change/connect/status", method = RequestMethod.POST)
	public int changeDeviceConnectStatus(@RequestParam("deviceIdx") int deviceIdx, @RequestParam("connect") Boolean connect) {
		return deviceNapper.changeDeviceConnectStatus(deviceIdx, connect);
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert/device", method = RequestMethod.POST)
	public int insert(@RequestBody Device device) {
		GroupPetMapping mapping = groupPetMappingMapper.isEmpty(device.getGroupCode());
		if(mapping != null) {
			return deviceNapper.insert(device);
		}else {
			String petGroupCode = MathUtil.getGroupId();
			GroupPetMapping groupPetMapping = new GroupPetMapping();
			groupPetMapping.setGroupCode(device.getGroupCode());
			groupPetMapping.setPetGroupCode(petGroupCode);
			groupPetMappingMapper.insert(groupPetMapping);
			return deviceNapper.insert(device);
		}
	}
}
