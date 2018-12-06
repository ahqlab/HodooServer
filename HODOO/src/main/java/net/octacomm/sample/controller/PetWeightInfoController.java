package net.octacomm.sample.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.PetWeightInfo;


@RequestMapping("/pet/weight")
@Controller
public class PetWeightInfoController {
	
	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;
	
	
	@Autowired
	private PetMapper petMapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public int regist(@RequestParam("petIdx") int petIdx, @RequestBody PetWeightInfo petWeightInfo) {
		petWeightInfoMapper.insert(petWeightInfo);
		return petMapper.registWeight(petWeightInfo.getId(), petIdx);
	}

	
	@ResponseBody
	@RequestMapping(value = "/get" , method = RequestMethod.POST)
	public PetWeightInfo get(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return petWeightInfoMapper.getPetWeightInformation(groupCode, petIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/bcs" , method = RequestMethod.POST)
	public PetWeightInfo getMyBcs(@RequestParam ("basicIdx") int basicIdx) {
		return petWeightInfoMapper.getBcs(basicIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return petWeightInfoMapper.InfoCheck(groupId, petId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		petMapper.resetWeight(petIdx);
		return petWeightInfoMapper.delete(id);
	}

}
