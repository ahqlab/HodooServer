package net.octacomm.sample.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.GroupsMapper;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetChronicDeseaseMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetBasicInfo;

@RequestMapping("/pet")
@Controller
public class PetController {
	
	@Autowired
	private GroupsMapper groupsMapper;

	@Autowired
	private PetBasicInfoMapper petBasicInfoMapper;

	@Autowired
	private PetChronicDeseaseMapper chronicDeseaseMapper;

	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;

	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;
	
	@Autowired
	private PetMapper petMapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/my/list", method = RequestMethod.POST)
	public List<PetBasicInfo> myList(HttpServletRequest request, @RequestParam("groupId") int groupId) {
		return  petBasicInfoMapper.getMyPetList(groupId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/my/registered/list", method = RequestMethod.POST)
	public List<PetBasicInfo> getMyRegisteredPetList(HttpServletRequest request, @RequestParam("groupId") String groupId) {
		return  petBasicInfoMapper.getMyRegisteredPetList(groupId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/basic/info/get", method = RequestMethod.POST)
	public PetBasicInfo login(HttpServletRequest request, @RequestParam("id") int id) {
		PetBasicInfo result = petBasicInfoMapper.getBasicInfoForPetId(id);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/my/pet/list", method = RequestMethod.POST)
	public List<Pet> myPetList(@RequestParam("groupCode") String groupCode){
		List<Pet> result = petMapper.myPetList(groupCode);
		return result;
	}
	
}
