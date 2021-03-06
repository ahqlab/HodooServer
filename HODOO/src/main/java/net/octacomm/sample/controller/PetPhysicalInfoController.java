package net.octacomm.sample.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.domain.PetPhysicalInfo;

@RequestMapping("/pet/physical")
@Controller
public class PetPhysicalInfoController {

	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;

	@Autowired
	private PetMapper petMapper;

	
	/**
	 * 피지컬 정보를 입력한다.
	 * 입력후 리턴 받은 idx 를 pet 테이블에 저장한다.
	 * @param petIdx
	 * @param petPhysicalInfo
	 * @return (int) 0 실패, 1 성공
	 */
	@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public int regist(@RequestParam("petIdx") int petIdx, @RequestBody PetPhysicalInfo petPhysicalInfo) {
		petPhysicalInfoMapper.insert(petPhysicalInfo);
		return petMapper.registPhysical(petPhysicalInfo.getId(), petIdx);
	}

	/**
	 * 피지컬 정보를 삭제한다.
	 * @param petIdx
	 * @param id
	 * @return (int) 0 실패, 1 성공
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		petMapper.resetPhysical(petIdx);
		return petPhysicalInfoMapper.delete(id);
	}
	
	/**
	 * 피지컬 상세 정보를 가져온다.
	 * @param groupCode
	 * @param petIdx
	 * @return PetPhysicalInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public PetPhysicalInfo getPetPhysicalInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return petPhysicalInfoMapper.getPetPhysicalInformation(groupCode, petIdx);
	}

	/*@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return petPhysicalInfoMapper.InfoCheck(groupId, petId);
	}*/

}
