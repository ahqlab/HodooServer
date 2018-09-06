package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetPhysicalInfo;


@RequestMapping("/pet/physical")
@Controller
public class PetPhysicalInfoController {
	
	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/regist" , method = RequestMethod.POST)
	public int regist(@RequestBody PetPhysicalInfo petPhysicalInfo) {
		System.err.println("XXXXXXXXXXXXXXXXXXXXXXXX");
		petPhysicalInfoMapper.get(petPhysicalInfo.getPetId());
		return petPhysicalInfoMapper.insert(petPhysicalInfo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	public int delete(@RequestParam ("petId") int id) {
		return petPhysicalInfoMapper.delete(id);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get" , method = RequestMethod.POST)
	public PetPhysicalInfo get(@RequestParam ("petId") int petId) {
		return petPhysicalInfoMapper.get(petId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return petPhysicalInfoMapper.InfoCheck(groupId, petId);
	}

}
