package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetChronicDeseaseMapper;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetChronicDisease;

@RequestMapping("/chronic/desease")
@Controller
public class PetChronicDeseaseController {
	
	@Autowired
	private PetChronicDeseaseMapper chronicDeseaseMapper;
	
	@ResponseBody
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	public int delete(@RequestParam("petId") int id) {
		return chronicDeseaseMapper.delete(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/regist" , method = RequestMethod.POST)
	public int regist(@RequestBody PetChronicDisease chronicDesease) {
		return chronicDeseaseMapper.insert(chronicDesease);
	}
	
	@ResponseBody
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	public List<PetChronicDisease> regist(@RequestParam("groupId") int groupId) {
		return chronicDeseaseMapper.list(groupId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetChronicDisease basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("id") int petId) {
		return chronicDeseaseMapper.InfoCheck(groupId, petId);
	}
}
