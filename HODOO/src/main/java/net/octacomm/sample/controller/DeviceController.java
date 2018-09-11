package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.domain.Device;

@RequestMapping("/device")
@Controller
public class DeviceController {
	
	@Autowired private DeviceMapper deviceNapper;

	@ResponseBody
	@RequestMapping(value = "/my/device/list", method = RequestMethod.POST)
	public List<Device> myDeviceList(@RequestParam("groupCode") String groupCode) {
		return deviceNapper.myDeviceList(groupCode);
	}
}
