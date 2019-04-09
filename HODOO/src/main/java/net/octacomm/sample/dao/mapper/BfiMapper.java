package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.BfiAnswer;
import net.octacomm.sample.domain.BfiModel;
import net.octacomm.sample.domain.Country;
import net.octacomm.sample.domain.DefaultParam;

public interface BfiMapper extends CRUDMapper<BfiModel, DefaultParam, Integer>{

	@Select("SELECT q.id, q.question, ( SELECT GROUP_CONCAT(a.id SEPARATOR ',') FROM bfi_answer a WHERE q.id = a.question_id ) AS answer_ids, q.additional_id FROM bfi_question q WHERE q.type = #{type } AND q.lang = #{location }")
	public List<BfiModel> getBfi( @Param("location") String location, @Param("type") int type );
	
	@Select("SELECT * FROM bfi_answer WHERE question_id = #{questionId }")
	public List<BfiAnswer> getBfiAnswer( @Param( "questionId" ) int questionId );
}
