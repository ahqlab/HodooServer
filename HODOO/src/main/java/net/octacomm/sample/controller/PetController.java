package net.octacomm.sample.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetBreedMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetBreed;


@RequestMapping("/pet")
@Controller
public class PetController {
	
	@Autowired
	private PetBasicInfoMapper petBasicInfoMapper;

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
	@RequestMapping(value = "/my/pet/listResult.do", method = RequestMethod.POST)
	public int[] myPetListResult(@RequestParam("groupCode") String groupCode){
		List<Pet> pets = petMapper.myPetList(groupCode);
		int resultCode = HodooConstant.PET_REGIST_FAILED;
		if ( !pets.isEmpty() ) {
			if ( pets.size() == 1 ) {
				if ( pets.get(0).getBasic() == 0 )
					resultCode = HodooConstant.PET_REGIST_FAILED;
				else if ( pets.get(0).getDisease() == 0 )
					resultCode = HodooConstant.PET_NOT_REGIST_DISEASES;
				else if ( pets.get(0).getPhysical() == 0 )
					resultCode = HodooConstant.PET_NOT_REGIST_PHYSICAL;
				else if ( pets.get(0).getWeight() == 0 )
					resultCode = HodooConstant.PET_NOT_REGIST_WEIGHT;
				else 
					resultCode = HodooConstant.PET_REGIST_SUCESS;
			} else {
				resultCode = HodooConstant.PET_REGIST_SUCESS;
			}
		}
		
		int result[] = new int[1];
		
		switch (resultCode) {
		case HodooConstant.PET_REGIST_SUCESS:
		case HodooConstant.PET_REGIST_FAILED:
			result[0] = resultCode;
			break;
		default:
			result = new int[2];
			result[0] = resultCode;
			result[1] = pets.get(0).getPetIdx();
			break;
		}
		return result;
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
	
	@ResponseBody
	@RequestMapping(value = "/get/type.do", method = RequestMethod.POST)
	public int getPetType( @RequestParam("petIdx") int petIdx ){
		return petBasicInfoMapper.getPetType(petIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/make/it/invisible.do", method = RequestMethod.POST)
	public CommonResponce<Integer> makeItInvisible( @RequestParam("petIdx") int petIdx ){
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		responce.setDomain(petMapper.makeItInvisible(petIdx));
		//responce.setStatus("200");
		return responce;
	}
	
}
