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

	
	/**
	 * 등록 기기 리스트를 가져온다.
	 * @param groupCode
	 * @return List<Device>
	 */
	@ResponseBody
	@RequestMapping(value = "/my/device/list.do", method = RequestMethod.POST)
	public List<Device> myDeviceList(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceList(groupCode);
	}
	
	
	/**
	 * 기기 등록 카운트를 가져온다.
	 * @param groupCode
	 * @return 기기등록 수
	 */
	@ResponseBody
	@RequestMapping(value = "/my/device/listResult.do", method = RequestMethod.POST)
	public int myDeviceListResult(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceListCount(groupCode);
	}

	/**
	 * 	기기등록 상태 변경
	 * @param deviceIdx
	 * @param connect
	 * @return 0 실패, 1 성공
	 */
	@ResponseBody
	@RequestMapping(value = "/change/connect/status.do", method = RequestMethod.POST)
	public int changeDeviceConnectStatus(@RequestParam("deviceIdx") int deviceIdx,
			@RequestParam("connect") Boolean connect) {
		return deviceNapper.changeDeviceConnectStatus(deviceIdx, connect);
	}

	/**
	 * 디바이스 연결 상태를 변경한다.
	 * @param groupCode
	 * @param deviceIdx
	 * @param isDel
	 * @return 0 실패, 1 성공
	 */
	@ResponseBody
	@RequestMapping(value = "/change/connection.do", method = RequestMethod.POST)
	public int changeDeviceConnection(@RequestParam("groupCode") String groupCode, @RequestParam("deviceIdx") int deviceIdx, @RequestParam("isDel") Boolean isDel) {
		return deviceNapper.changeDeviceConnection(groupCode, deviceIdx, isDel);
	}
	
	
	/**
	 * 디바이스를 등록한다.
	 * PetGroupMapping 에 등록된  DEVICE 가 없으면
	 * PetGroupMapping 및 DEVICE 등록
	 * PetGroupMapping 이 존재하면 DEVICE만 등록
	 * 이미 DEVICE 가 등록되었을 경우 > 만약 디바이스의 isDel 이 DISCONNECTED 일시  CONNECTED 변경
	 * 그 외 100 리턴 100 > 이미등록된 디바이스 입니다.(앱에서 처리)
	 * 
	 * @param device
	 * @return 100 (이미등록된 디바이스 입니다.(앱에서 처리)) , 0 등록 실패, 1 성공
	 */
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
	
}
