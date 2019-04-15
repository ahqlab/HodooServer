package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.PetChronicDiseaseMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.PetChronicDisease;

@RequestMapping("/android/chronic/desease")
@Controller
public class PetChronicDiseaseControllerForAndroid {
	
	@Autowired
	private PetChronicDiseaseMapper chronicDiseaseMapper;
	
	@Autowired
	private PetMapper petMapper;
	
	@ResponseBody
	@RequestMapping(value = "/delete.do" , method = RequestMethod.POST)
	public CommonResponce<Integer> delete(@RequestParam("petIdx") int petIdx, @RequestParam("diseaseIdx") int diseaseIdx) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		petMapper.resetDisease(petIdx);
		int obj = chronicDiseaseMapper.delete(diseaseIdx);
		if(obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
		
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/regist.do" , method = RequestMethod.POST)
	public CommonResponce<Integer> regist(@RequestBody PetChronicDisease chronicDesease, @RequestParam("petIdx") int petIdx) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		chronicDiseaseMapper.insert(chronicDesease);
		int obj = petMapper.registDisease(chronicDesease.getId(), petIdx);
		if(obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}
	
	
	/* 사용안함.*/
	@ResponseBody
	@RequestMapping(value = "/list.do" , method = RequestMethod.POST)
	public List<PetChronicDisease> regist(@RequestParam("groupId") int groupId) {
		return chronicDiseaseMapper.list(groupId);
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/info/check.do", method = RequestMethod.POST)
	public PetChronicDisease basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("id") int petId) {
		return chronicDiseaseMapper.InfoCheck(groupId, petId);
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public CommonResponce<PetChronicDisease> getBasicInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		CommonResponce<PetChronicDisease> responce = new CommonResponce<PetChronicDisease>();
		PetChronicDisease obj = chronicDiseaseMapper.getDiseaseInformation(groupCode, petIdx);
		if(obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
	
		return responce;
	}
}
