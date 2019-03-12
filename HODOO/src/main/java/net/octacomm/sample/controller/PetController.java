package net.octacomm.sample.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.GroupsMapper;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetBreedMapper;
import net.octacomm.sample.dao.mapper.PetChronicDiseaseMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetBreed;


@RequestMapping("/pet")
@Controller
public class PetController {
	
	@Autowired
	private GroupsMapper groupsMapper;

	@Autowired
	private PetBasicInfoMapper petBasicInfoMapper;

	@Autowired
	private PetChronicDiseaseMapper chronicDeseaseMapper;

	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;

	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;
	
	@Autowired
	private PetMapper petMapper;
	
	@Autowired
	private PetBreedMapper breedMapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/my/list.do", method = RequestMethod.POST)
	public List<PetBasicInfo> myList(HttpServletRequest request, @RequestParam("groupId") int groupId) {
		return  petBasicInfoMapper.getMyPetList(groupId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/my/registered/list.do", method = RequestMethod.POST)
	public List<PetBasicInfo> getMyRegisteredPetList(HttpServletRequest request, @RequestParam("groupId") String groupId) {
		return  petBasicInfoMapper.getMyRegisteredPetList(groupId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/basic/info/get.do", method = RequestMethod.POST)
	public PetBasicInfo login(HttpServletRequest request, @RequestParam("id") int id) {
		return petBasicInfoMapper.getBasicInfoForPetId(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/my/pet/list.do", method = RequestMethod.POST)
	public List<Pet> myPetList(@RequestParam("groupCode") String groupCode){
		return petMapper.myPetList(groupCode);
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/infos.do", method = RequestMethod.POST)
	public PetAllInfos petAllInfos(@RequestParam("petIdx") int petIdx){
		return petMapper.allInfoOnThePet(petIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/all/getBreed.do", method = RequestMethod.POST)
	public List<PetBreed> getAllBreed( @RequestParam("location") String location ){
		List<PetBreed> list = breedMapper.getAllList( location );
		return list;
	}
	
	
}
