package net.octacomm.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.octacomm.sample.dao.mapper.GroupsMapper;
import net.octacomm.sample.domain.Groups;
import net.octacomm.sample.utils.MathUtil;

@Service
public class GroupsServiceImpl implements GroupsService{
	
	@Autowired
	private GroupsMapper groupsMapper;

	@Override
	public Groups createGroups() {
		Groups group = new Groups();
		group.setGroupId(MathUtil.getGroupId());
		groupsMapper.insert(group);
		return group;
	}

}
