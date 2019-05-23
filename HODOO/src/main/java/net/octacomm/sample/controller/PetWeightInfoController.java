package net.octacomm.sample.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.BfiMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.BfiModel;
import net.octacomm.sample.domain.BfiQuestion;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.PetWeightInfo;


@RequestMapping("/pet/weight")
@Controller
public class PetWeightInfoController {
	
	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;
	
	@Autowired
	private PetMapper petMapper;
	
	@Autowired
	private BfiMapper bfiMapper;
	
	
	@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public int regist(@RequestParam("petIdx") int petIdx, @RequestBody PetWeightInfo petWeightInfo) {
		petWeightInfoMapper.insert(petWeightInfo);
		return petMapper.registWeight(petWeightInfo.getId(), petIdx);
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
	
	// 1: 강아지 2 : 고양이, and location 
	// do) 항문 가져온다 
	@ResponseBody
	@RequestMapping(value = "/bfi.do" , method = RequestMethod.POST)
	public List<BfiModel> getMyBfi(@RequestParam("location") String location, @RequestParam("type") int type) {
		List<BfiModel> bfiModel = bfiMapper.getBfi( location, type );
		for ( int i = 0; i < bfiModel.size(); i++ ) {
			bfiModel.get(i).setAnswers(bfiMapper.getBfiAnswer(location, bfiModel.get(i).getId()));
		}
		return bfiModel;
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
