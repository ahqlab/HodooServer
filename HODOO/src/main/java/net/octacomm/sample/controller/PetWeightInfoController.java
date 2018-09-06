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
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.PetWeightInfo;


@RequestMapping("/pet/weight")
@Controller
public class PetWeightInfoController {
	
	@Autowired
	private PetWeightInfoMapper mapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/regist" , method = RequestMethod.POST)
	public int regist(@RequestBody PetWeightInfo petWeightInfo) {
		mapper.delete(petWeightInfo.getPetId());
		return mapper.insert(petWeightInfo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get" , method = RequestMethod.POST)
	public PetWeightInfo get(@RequestParam ("petId") int petId) {
		return mapper.get(petId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/my/bcs" , method = RequestMethod.POST)
	public String getMyBcs(@RequestParam ("groupId") String groupId, @RequestParam ("petId") int petId) {
		return mapper.getMyBcs(groupId, petId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return mapper.InfoCheck(groupId, petId);
	}


}
