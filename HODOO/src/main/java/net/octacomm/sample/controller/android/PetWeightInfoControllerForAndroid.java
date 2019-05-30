package net.octacomm.sample.controller.android;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.BfiMapper;
import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetWeightInfoMapper;
import net.octacomm.sample.dao.mapper.RealTimeWeightMapper;
import net.octacomm.sample.dao.mapper.WeightGoalChartMapper;
import net.octacomm.sample.domain.BfiModel;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.PetWeightInfo;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.WeightGoalChart;

@RequestMapping("/android/pet/weight")
@Controller
public class PetWeightInfoControllerForAndroid {

	@Autowired
	private PetWeightInfoMapper petWeightInfoMapper;

	@Autowired
	private PetMapper petMapper;

	@Autowired
	private BfiMapper bfiMapper;

	@Autowired
	private WeightGoalChartMapper weightGoalChartMapper;
	
	@Autowired private RealTimeWeightMapper realTimeWeightMapper; 
	
	@Autowired private DeviceMapper deviceMapper;

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/regist.do", method = RequestMethod.POST) public int
	 * regist(@RequestParam("petIdx") int petIdx, @RequestBody PetWeightInfo
	 * petWeightInfo) { petWeightInfoMapper.insert(petWeightInfo); return
	 * petMapper.registWeight(petWeightInfo.getId(), petIdx); }
	 */

	@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public CommonResponce<Integer> regist(@RequestParam("petIdx") int petIdx , @RequestBody PetWeightInfo petWeightInfo) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		petWeightInfoMapper.insert(petWeightInfo);
		int obj = petMapper.registWeight(petWeightInfo.getId(), petIdx);
		if (obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}
	
	public int addRealTimeWeight(int petIdx, float weight, String groupCode) {
		Device device = deviceMapper.getDeviceInfoByGroupCode(groupCode);
		RealTimeWeight realTimeWeight = new RealTimeWeight();
		realTimeWeight.setValue(weight);
		realTimeWeight.setMac(device.getSerialNumber());
		realTimeWeight.setType(0);
		realTimeWeight.setTag(String.valueOf(petIdx));
		return realTimeWeightMapper.insert(realTimeWeight);
	}

	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public CommonResponce<PetWeightInfo> get(@RequestParam("groupCode") String groupCode,
			@RequestParam("petIdx") int petIdx) {
		CommonResponce<PetWeightInfo> responce = new CommonResponce<PetWeightInfo>();
		PetWeightInfo obj = petWeightInfoMapper.getPetWeightInformation(groupCode, petIdx);
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
	@RequestMapping(value = "/bcs.do", method = RequestMethod.POST)
	public CommonResponce<PetWeightInfo> getMyBcs(@RequestParam("basicIdx") int basicIdx) {
		CommonResponce<PetWeightInfo> responce = new CommonResponce<PetWeightInfo>();
		PetWeightInfo obj = petWeightInfoMapper.getBcs(basicIdx);
		if (obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;

	}

	// 1: 강아지 2 : 고양이, and location
	// do) 항문 가져온다
	@ResponseBody
	@RequestMapping(value = "/bfi.do", method = RequestMethod.POST)
	public CommonResponce<List<BfiModel>> getMyBfi(@RequestParam("location") String location,
			@RequestParam("type") int type) {
		CommonResponce<List<BfiModel>> responce = new CommonResponce<List<BfiModel>>();
		List<BfiModel> bfiModel = bfiMapper.getBfi(location, type);
		for (int i = 0; i < bfiModel.size(); i++) {
			bfiModel.get(i).setAnswers(bfiMapper.getBfiAnswer(location, bfiModel.get(i).getId()));
		}
		if (bfiModel.size() > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(bfiModel);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(bfiModel);
		}
		return responce;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/info/check.do", method = RequestMethod.POST) public
	 * PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String
	 * groupId, @RequestParam("petId") int petId) { return
	 * petWeightInfoMapper.InfoCheck(groupId, petId); }
	 */

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/delete.do", method = RequestMethod.POST) public int
	 * delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
	 * petMapper.resetWeight(petIdx); return petWeightInfoMapper.delete(id); }
	 */

	@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public CommonResponce<Integer> delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		petMapper.resetWeight(petIdx);
		int obj = petWeightInfoMapper.delete(id);
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
	@RequestMapping(value = "/get/weight/goal.do", method = RequestMethod.POST)
	public CommonResponce<WeightGoalChart> createGoalKg(@RequestParam("currentWeight") float currentWeight,
			@RequestParam("bodyFat") int bodyFat, @RequestParam("petType") int petType) {
		CommonResponce<WeightGoalChart> responce = new CommonResponce<WeightGoalChart>();
		WeightGoalChart obj = weightGoalChartMapper.getWeightGoal(currentWeight, bodyFat, petType);
		if (obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}
		
	/**
	 * 전주 대비 감량 비율을 가져온다. 
	 * @param 	groupCode
	 * @return	CommonResponce<Float>
	 */
	@ResponseBody
	@RequestMapping(value = "/getWeekRate.do", method = RequestMethod.POST)
	public CommonResponce<Float> getWeekRate(@RequestParam("groupCode") String groupCode) {
		Device device = deviceMapper.getDeviceInfoByGroupCode(groupCode);
		CommonResponce<Float> responce = new CommonResponce<Float>();
		if ( device != null ) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain( petWeightInfoMapper.getWeekRate(device.getSerialNumber()) );
		} else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain( 0f );
		}
		
		
		return responce;
	}

}
