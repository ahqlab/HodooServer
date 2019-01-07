package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.User;

public interface FirebaseMapper extends CRUDMapper<InvitationRequest, DefaultParam, Integer> {
	
	public String TABLE_NAME = "invitation_request";

	@Insert("INSERT INTO " + TABLE_NAME + " (fromUserIdx, toUserIdx) VALUE (#{fromUserIdx}, #{toUserIdx})")
	@Override
	int insert(InvitationRequest domain);

	@Override
	InvitationRequest get(Integer id);

	@Override
	int update(InvitationRequest domain);

	@Override
	int delete(Integer id);

	@Override
	int getCountByParam(DefaultParam param);

	@Override
	List<InvitationRequest> getListByParam(int startRow, int pageSize, DefaultParam param);

	@Override
	List<InvitationRequest> getList();
	
	@Select("select count(*) from " + TABLE_NAME + " where toUserIdx = #{toUserIdx} and fromUserIdx = #{fromUserIdx}")
	int getCount(InvitationRequest domain);
	
	@Update("UPDATE " + TABLE_NAME + " SET created = STR_TO_DATE(#{created}, '%Y-%m-%d %H:%i:%s') WHERE toUserIdx = #{toUserIdx} and fromUserIdx = #{fromUserIdx}")
	int updateCreated(InvitationRequest domain);
	
}
