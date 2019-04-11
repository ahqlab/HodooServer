package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.BfiAnswer;
import net.octacomm.sample.domain.BfiModel;
import net.octacomm.sample.domain.Country;
import net.octacomm.sample.domain.DefaultParam;

public interface BfiMapper extends CRUDMapper<BfiModel, DefaultParam, Integer>{

	@Select("SELECT q.id, q.question, ( SELECT GROUP_CONCAT(a.id SEPARATOR ',') FROM bfi_answer a WHERE q.id = a.question_id ) AS answer_ids, ba.id AS additional_id, ba.title, ba.info, ba.image FROM bfi_question q LEFT JOIN bfi_addition ba ON q.additional_id = ba.id WHERE q.type = #{type } AND q.lang = #{location }  ORDER BY q.order_no ASC")
	public List<BfiModel> getBfi( @Param("location") String location, @Param("type") int type );
	
	@Select("SELECT * FROM bfi_answer WHERE question_id = #{questionId } order by order_no ASC")
	public List<BfiAnswer> getBfiAnswer( @Param( "questionId" ) int questionId );
}
