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
/**
 * 펫 질병 관련
 * @author silve
 *
 */
@RequestMapping("/chronic/desease")
@Controller
public class PetChronicDiseaseController {
	
	@Autowired
	private PetChronicDiseaseMapper chronicDiseaseMapper;
	
	@Autowired
	private PetMapper petMapper;
	
	/**
	 * 질별 삭제
	 * @param petIdx
	 * @param diseaseIdx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.do" , method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("diseaseIdx") int diseaseIdx) {
		petMapper.resetDisease(petIdx);
		return chronicDiseaseMapper.delete(diseaseIdx);
	}
	
	/**
	 * 질별등록
	 * @param chronicDesease
	 * @param petIdx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/regist.do" , method = RequestMethod.POST)
	public int regist(@RequestBody PetChronicDisease chronicDesease, @RequestParam("petIdx") int petIdx) {
		System.out.println( "debug" );
		chronicDiseaseMapper.insert(chronicDesease);
		return petMapper.registDisease(chronicDesease.getId(), petIdx);
	}
	
	
	/**
	 * 질별 리스트
	 * @param groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list.do" , method = RequestMethod.POST)
	public List<PetChronicDisease> regist(@RequestParam("groupId") int groupId) {
		return chronicDiseaseMapper.list(groupId);
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/info/check.do", method = RequestMethod.POST)
	public PetChronicDisease basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("id") int petId) {
		return chronicDiseaseMapper.InfoCheck(groupId, petId);
	}
	*/
	
	/**
	 * 질별 상세 정보
	 * @param groupCode
	 * @param petIdx
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public PetChronicDisease getBasicInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return chronicDiseaseMapper.getDiseaseInformation(groupCode, petIdx);
	}
}
