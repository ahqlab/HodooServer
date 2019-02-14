package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetChronicDiseaseMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.domain.PetChronicDisease;

@RequestMapping("/chronic/desease")
@Controller
public class PetChronicDeseaseController {
	
	@Autowired
	private PetChronicDiseaseMapper chronicDeseaseMapper;
	
	@Autowired
	private PetMapper petMapper;
	
	@ResponseBody
	@RequestMapping(value = "/delete.do" , method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("diseaseIdx") int diseaseIdx) {
		petMapper.resetDisease(petIdx);
		return chronicDeseaseMapper.delete(diseaseIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/regist.do" , method = RequestMethod.POST)
	public int regist(@RequestBody PetChronicDisease chronicDesease, @RequestParam("petIdx") int petIdx) {
		chronicDeseaseMapper.insert(chronicDesease);
		return petMapper.registDisease(chronicDesease.getId(), petIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.do" , method = RequestMethod.POST)
	public List<PetChronicDisease> regist(@RequestParam("groupId") int groupId) {
		return chronicDeseaseMapper.list(groupId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/info/check.do", method = RequestMethod.POST)
	public PetChronicDisease basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("id") int petId) {
		return chronicDeseaseMapper.InfoCheck(groupId, petId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public PetChronicDisease getBasicInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return chronicDeseaseMapper.getDiseaseInformation(groupCode, petIdx);
	}
}
