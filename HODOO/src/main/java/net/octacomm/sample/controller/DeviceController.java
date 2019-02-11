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
import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.domain.ArrayListDevice;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.domain.UserGroupMapping;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/device")
@Controller
public class DeviceController {

	@Autowired
	private DeviceMapper deviceNapper;
	@Autowired
	private GroupPetMappingMapper groupPetMappingMapper;
	
	@Autowired
	private UserGroupMappingMapper userGroupMappingMapper;

	@ResponseBody
	@RequestMapping(value = "/my/device/list.do", method = RequestMethod.POST)
	public List<Device> myDeviceList(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceList(groupCode);
	}

	@ResponseBody
	@RequestMapping(value = "/change/connect/status.do", method = RequestMethod.POST)
	public int changeDeviceConnectStatus(@RequestParam("deviceIdx") int deviceIdx,
			@RequestParam("connect") Boolean connect) {
		return deviceNapper.changeDeviceConnectStatus(deviceIdx, connect);
	}

	@ResponseBody
	@RequestMapping(value = "/change/connection.do", method = RequestMethod.POST)
	public int changeDeviceConnection(@RequestParam("groupCode") String groupCode, @RequestParam("deviceIdx") int deviceIdx, @RequestParam("isDel") Boolean isDel) {
		return deviceNapper.changeDeviceConnection(groupCode, deviceIdx, isDel);
	}

	@ResponseBody
	@RequestMapping(value = "/insert/device.do", method = RequestMethod.POST)
	public int insert(@RequestBody Device device) {
		GroupPetMapping mapping = groupPetMappingMapper.isEmpty(device.getGroupCode());
		if (mapping != null) {
			List<Device> registed = deviceNapper.getRegisted(device);
			if (registed.size() > 0) {
				for (Device dv : registed) {
					if (dv.getSerialNumber().matches(device.getSerialNumber())) {
						if (dv.getIsDel().matches("DISCONNECTED")) {
							if(groupPetMappingMapper.isEmpty(device.getGroupCode()) != null) {
								return deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true);
							}else {
								String petGroupCode = MathUtil.getGroupId();
								GroupPetMapping groupPetMapping = new GroupPetMapping();
								groupPetMapping.setGroupCode(device.getGroupCode());
								groupPetMapping.setPetGroupCode(petGroupCode);
								groupPetMappingMapper.insert(groupPetMapping);
								return deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true);
							}
						}
					}

				}
				return 100;
			}
			
			userGroupMappingMapper.setMaster(device.getGroupCode());
			
			return deviceNapper.insert(device);
		} else {
			List<Device> registed = deviceNapper.getRegisted(device);
			if (registed.size() > 0) {
				for (Device dv : registed) {
					if (dv.getSerialNumber().matches(device.getSerialNumber())) {
						if (dv.getIsDel().matches("DISCONNECTED")) {
							if(groupPetMappingMapper.isEmpty(device.getGroupCode()) != null) {
								return deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true);
							}else {
								String petGroupCode = MathUtil.getGroupId();
								GroupPetMapping groupPetMapping = new GroupPetMapping();
								groupPetMapping.setGroupCode(device.getGroupCode());
								groupPetMapping.setPetGroupCode(petGroupCode);
								groupPetMappingMapper.insert(groupPetMapping);
								return deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true);
							}
							
						}
					}

				}
				return 100;
			}
			String petGroupCode = MathUtil.getGroupId();
			GroupPetMapping groupPetMapping = new GroupPetMapping();
			groupPetMapping.setGroupCode(device.getGroupCode());
			groupPetMapping.setPetGroupCode(petGroupCode);
			groupPetMappingMapper.insert(groupPetMapping);
			return deviceNapper.insert(device);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/insert/device2.do", method = RequestMethod.POST)
	public Device insert2(@RequestBody Device device) {
		return device;
	}
}
