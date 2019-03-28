package net.octacomm.sample.controller.ios;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.PetCommonResponse;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.PetWeightInfo;
import net.octacomm.sample.message.ResultMessage;


@RequestMapping("/ios/pet/weight")
@Controller
public class IOSPetWeightInfoController {
	
	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;
	
	
	@Autowired
	private PetMapper petMapper;
	
	
	/*@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public PetCommonResponse<PetWeightInfo> regist(@RequestBody Map<String, Object> param) {
		
		PetCommonResponse<PetWeightInfo> commonResponse = new PetCommonResponse<PetWeightInfo>();
		
		int bcs = (Integer) param.get("bcs");
		int petIdx = (Integer) param.get("petIdx");
		
		PetWeightInfo petWeightInfo = new PetWeightInfo();
		petWeightInfo.setBcs(bcs);
		
		petWeightInfoMapper.insert(petWeightInfo);
		if(petMapper.registWeight(petWeightInfo.getId(), petIdx) != 0) {
			commonResponse.setDomain(petWeightInfoMapper.get(petWeightInfo.getId()));
			commonResponse.setResultMessage(ResultMessage.SUCCESS);
		}else {
			commonResponse.setResultMessage(ResultMessage.FAILED);
		}
		return commonResponse;
	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public int regist(@RequestParam("petIdx") int petIdx, @RequestBody PetWeightInfo petWeightInfo) {
		System.err.println("petWeightInfo : " + petWeightInfo);
		if(petWeightInfo.getId() != 0) {
			return petWeightInfoMapper.update(petWeightInfo);
		}else {
			petWeightInfoMapper.insert(petWeightInfo);
			return petMapper.registWeight(petWeightInfo.getId(), petIdx);
		}
	
	}

	
	@ResponseBody
	@RequestMapping(value = "/get.do" , method = RequestMethod.POST)
	public PetWeightInfo get(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return petWeightInfoMapper.getPetWeightInformation(groupCode, petIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/bcs.do" , method = RequestMethod.POST)
	public PetWeightInfo getMyBcs(@RequestParam ("basicIdx") int basicIdx) {
		return petWeightInfoMapper.getBcs(basicIdx);
	}
	
	@ResponseBody
	@RequestMapping(value = "/info/check.do", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return petWeightInfoMapper.InfoCheck(groupId, petId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		petMapper.resetWeight(petIdx);
		return petWeightInfoMapper.delete(id);
	}

}
