package net.octacomm.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.GroupsMapper;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetChronicDeseaseMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.domain.Groups;

@RequestMapping("/groups")
@Controller
public class GroupsController {

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

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public Groups regist(@RequestBody Groups group) {
		return groupsMapper.getGroups(group);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/list", method = RequestMethod.POST)
	public List<Groups> getList(@RequestBody Groups group) {
		return groupsMapper.getListGroups(group);
	}
	

	@ResponseBody
	@RequestMapping(value = "/is/regist", method = RequestMethod.POST)
	public Groups isRegist(@RequestBody Groups group) {
		if (petBasicInfoMapper.getBasicInfo(group.getPetId()) != null) {
			if (chronicDeseaseMapper.list(group.getPetId()).size() > 0) {
				if (petPhysicalInfoMapper.get(group.getPetId()) != null) {
					if (petWeightInfoMapper.get(group.getPetId()) != null) {
						return groupsMapper.getGroups(group);
					}
				}
			}
		}
		return null;
	}
}
