package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.PetGroupMappingMapper;
import net.octacomm.sample.domain.PetGroupMapping;


@RequestMapping("/pet/group/mapping")
@Controller
public class PetGroupMappingController {
	
	@Autowired
	private PetGroupMappingMapper petGroupMappingMapper;
	
	@ResponseBody
	@RequestMapping(value = "/my/pet/list", method = RequestMethod.POST)
	public List<PetGroupMapping> myPetList(@RequestParam("groupId") int groupId) {
		return petGroupMappingMapper.getMyPetList(groupId);
	}
}
