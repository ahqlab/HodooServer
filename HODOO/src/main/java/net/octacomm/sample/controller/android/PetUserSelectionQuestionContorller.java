package net.octacomm.sample.controller.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetUserSelectionQuestionMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.PetUserSelectionQuestion;

@RequestMapping("/android/pet/question")
@Controller
public class PetUserSelectionQuestionContorller {
	
	@Autowired
	private PetUserSelectionQuestionMapper petUserSelectionQuestionMapper; 
	
	@Autowired
	private PetMapper petMapper;
	
	@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public CommonResponce<Integer> regist(@RequestParam("petIdx") int petIdx , @RequestBody PetUserSelectionQuestion petUserSelectionQuestion) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		petUserSelectionQuestionMapper.insert(petUserSelectionQuestion);
		int obj = petMapper.registSltQst(petUserSelectionQuestion.getQuestionIdx(), petIdx);
		if (obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}

	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public CommonResponce<PetUserSelectionQuestion> get(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		CommonResponce<PetUserSelectionQuestion> responce = new CommonResponce<PetUserSelectionQuestion>();
		PetUserSelectionQuestion obj = petUserSelectionQuestionMapper.getPetWeightInformation(groupCode, petIdx);
		if (obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}


	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public CommonResponce<Integer> delete(@RequestParam("petIdx") int petIdx, @RequestParam("questionIdx") int questionIdx) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		petMapper.resetSltQst(petIdx);
		int obj = petUserSelectionQuestionMapper.delete(questionIdx);
		if (obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}
}
