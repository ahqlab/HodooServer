package net.octacomm.sample.controller.ios;

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
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/ios/device")
@Controller
public class IOSDeviceController {
	
	@Autowired private DeviceMapper deviceNapper;
	@Autowired private GroupPetMappingMapper groupPetMappingMapper;

	@ResponseBody
	@RequestMapping(value = "/my/device/list.do", method = RequestMethod.POST)
	public List<Device> myDeviceList(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceList(groupCode);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/test/delete/device.do", method = RequestMethod.GET)
	public int delete(@RequestParam("serialNumber") String serialNumber) {
		return deviceNapper.testDeleteDevice(serialNumber);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/insert/device.do", method = RequestMethod.POST)
	public CommonResponce<Device> insert(@RequestBody Device device) {
		CommonResponce<Device> responce;
		GroupPetMapping mapping = groupPetMappingMapper.isEmpty(device.getGroupCode());
		if (mapping != null) {
			List<Device> registed = deviceNapper.getRegisted(device);
			if (registed.size() > 0) {
				for (Device dv : registed) {
					if (dv.getSerialNumber().matches(device.getSerialNumber())) {
						if (dv.getIsDel().matches("DISCONNECTED")) {
							deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true);
							responce = new CommonResponce<Device>();
							responce.setDomain(deviceNapper.get(dv.getDeviceIdx()));
							responce.setResultMessage(ResultMessage.SUCCESS);
							return responce;
						}
					}

				}
				
				responce = new CommonResponce<Device>();
				responce.setDomain(new Device());
				responce.setResultMessage(ResultMessage.DUPLICATE_DEVICE);
				return responce;
			}
			deviceNapper.insert(device);
			responce = new CommonResponce<Device>();
			responce.setDomain(deviceNapper.get(device.getDeviceIdx()));
			responce.setResultMessage(ResultMessage.SUCCESS);
			return responce;
		} else {
			List<Device> registed = deviceNapper.getRegisted(device);
			if (registed.size() > 0) {
				for (Device dv : registed) {
					if (dv.getSerialNumber().matches(device.getSerialNumber())) {
						if (dv.getIsDel().matches("DISCONNECTED")) {
							deviceNapper.changeDeviceConnection(device.getGroupCode(), dv.getDeviceIdx(), true);
							responce = new CommonResponce<Device>();
							responce.setDomain(deviceNapper.get(dv.getDeviceIdx()));
							responce.setResultMessage(ResultMessage.SUCCESS);
							return responce;
						}
					}

				}
				responce = new CommonResponce<Device>();
				responce.setDomain(new Device());
				responce.setResultMessage(ResultMessage.DUPLICATE_DEVICE);
				return responce;
			}
			String petGroupCode = MathUtil.getGroupId();
			GroupPetMapping groupPetMapping = new GroupPetMapping();
			groupPetMapping.setGroupCode(device.getGroupCode());
			groupPetMapping.setPetGroupCode(petGroupCode);
			groupPetMappingMapper.insert(groupPetMapping);
			deviceNapper.insert(device);
			
			responce = new CommonResponce<Device>();
			responce.setDomain(deviceNapper.get(device.getDeviceIdx()));
			responce.setResultMessage(ResultMessage.SUCCESS);
			return responce;
		}
	}
}
