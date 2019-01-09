package net.octacomm.sample.controller.ios;


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
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.domain.PetCommonResponse;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.PetWeightInfo;
import net.octacomm.sample.message.ResultMessage;

@RequestMapping("/ios/pet/physical")
@Controller
public class IOSPetPhysicalInfoController {

	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;

	@Autowired
	private PetMapper petMapper;

	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public PetCommonResponse<PetPhysicalInfo> regist(@RequestBody Map<String, Object> param) {
		PetCommonResponse<PetPhysicalInfo> commonResponse = new PetCommonResponse<PetPhysicalInfo>();
		
		String width = (String) param.get("width");
		String height = (String) param.get("height");
		String weight = (String) param.get("weight");
		int petIdx = (Integer) param.get("petIdx");
		
		PetPhysicalInfo petPhysicalInfo = new PetPhysicalInfo();
		petPhysicalInfo.setWidth(width);
		petPhysicalInfo.setHeight(height);
		petPhysicalInfo.setWeight(weight);
		
		petPhysicalInfoMapper.insert(petPhysicalInfo);
		if (petMapper.registPhysical(petPhysicalInfo.getId(), petIdx) != 0) {
			commonResponse.setDomain(petPhysicalInfoMapper.get(petPhysicalInfo.getId()));
			commonResponse.setResultMessage(ResultMessage.SUCCESS);
		} else {
			commonResponse.setResultMessage(ResultMessage.FAILED);
		}
		return commonResponse;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		petMapper.resetPhysical(petIdx);
		return petPhysicalInfoMapper.delete(id);
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public PetPhysicalInfo getPetPhysicalInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return petPhysicalInfoMapper.getPetPhysicalInformation(groupCode, petIdx);
	}

	/*@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return petPhysicalInfoMapper.InfoCheck(groupId, petId);
	}*/

}
