package net.octacomm.sample.controller.android;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.GroupPetMappingMapper;
import net.octacomm.sample.dao.mapper.UserGroupMappingMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.message.ResultMessage;

@RequestMapping("/android/device")
@Controller
public class DeviceControllerForAndroid {

	@Autowired
	private DeviceMapper deviceNapper;
	
	@Autowired
	private GroupPetMappingMapper groupPetMappingMapper;
	
	@Autowired
	private UserGroupMappingMapper userGroupMappingMapper;

	@ResponseBody
	@RequestMapping(value = "/my/device/list.do", method = RequestMethod.POST)
	public List<Device> myDeviceList(@RequestParam("groupCode") String groupCode) {
		CommonResponce<List<Device>> responce = new CommonResponce<List<Device>>();
		List<Device> list = deviceNapper.myDeviceList(groupCode);
		if(list.size() == 0) {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(list);
		}else {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(list);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value = "/my/device/listResult.do", method = RequestMethod.POST)
	public int myDeviceListResult(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceListCount(groupCode);
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
	public CommonResponce<Integer> insert(@RequestBody Device device) {
		//내 그룹에 등록된 기기들
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		GroupPetMapping mapping = groupPetMappingMapper.isEmpty(device.getGroupCode());
		String petGroupCode = UUID.randomUUID().toString().replace("-", "");
		//내 그룹에 등록된 기기들이 있다면.
		if (mapping != null) {
			//파라미터로 받은 시리얼 넘버의 기기 검색
			List<Device> registed = deviceNapper.getRegisted(device);
			//기기가 있다면.
			if (registed.size() > 0) {
				//돌린다.
				for (Device dv : registed) {
					//만약 등롣된 놈이 
					if (dv.getSerialNumber().matches(device.getSerialNumber())) {
						//연결이 해제되었고
						if (dv.getIsDel().matches("DISCONNECTED")) {
							//내 그룹에 등록된 기기라면
							if(groupPetMappingMapper.isEmpty(device.getGroupCode()) != null) {
								//상태를 변경한다.
								responce.setStatus(HodooConstant.OK_RESPONSE);
								responce.setDomain(deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true));
								return responce;
							//내 그룹에 등록된 기기가 아니라면
							}else {
								//신규 그룹 생성
								GroupPetMapping groupPetMapping = new GroupPetMapping();
								groupPetMapping.setGroupCode(device.getGroupCode());
								groupPetMapping.setPetGroupCode(petGroupCode);
								groupPetMappingMapper.insert(groupPetMapping);
								//디바이스 그룹코드와 커넥션을 변경한다.
								responce.setStatus(HodooConstant.OK_RESPONSE);
								responce.setDomain(deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true));
								return responce;
							}
						}
					}

				}
				responce.setStatus(HodooConstant.OK_RESPONSE);
				responce.setResultMessage(ResultMessage.DUPLICATE_DEVICE);
				responce.setDomain(100);
				return responce;
			}
			
			userGroupMappingMapper.setMaster(device.getGroupCode());
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(deviceNapper.insert(device));
			return responce;
			
		} else {
			List<Device> registed = deviceNapper.getRegisted(device);
			if (registed.size() > 0) {
				for (Device dv : registed) {
					if (dv.getSerialNumber().matches(device.getSerialNumber())) {
						if (dv.getIsDel().matches("DISCONNECTED")) {
							if(groupPetMappingMapper.isEmpty(device.getGroupCode()) != null) {
								
								responce.setStatus(HodooConstant.OK_RESPONSE);
								responce.setDomain(deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true));
								return responce;
								
							}else {
								System.err.println("grougCode : " + petGroupCode);
								GroupPetMapping groupPetMapping = new GroupPetMapping();
								groupPetMapping.setGroupCode(device.getGroupCode());
								groupPetMapping.setPetGroupCode(petGroupCode);
								groupPetMappingMapper.insert(groupPetMapping);
								//디바이스 그룹코드와 커넥션을 변경한다.
								responce.setStatus(HodooConstant.OK_RESPONSE);
								responce.setDomain(deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true));
								return responce;
							}
							
						}
					}

				}
				responce.setStatus(HodooConstant.OK_RESPONSE);
				responce.setResultMessage(ResultMessage.DUPLICATE_DEVICE);
				responce.setDomain(100);
				return responce;
			}
			//신규 그룹 생성
			GroupPetMapping groupPetMapping = new GroupPetMapping();
			groupPetMapping.setGroupCode(device.getGroupCode());
			groupPetMapping.setPetGroupCode(petGroupCode);
			groupPetMappingMapper.insert(groupPetMapping);
			//신규 디바이스 등록
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(deviceNapper.insert(device));
			return responce;
			
			
		}
	}

	@ResponseBody
	@RequestMapping(value = "/insert/device2.do", method = RequestMethod.POST)
	public Device insert2(@RequestBody Device device) {
		return device;
	}
}
