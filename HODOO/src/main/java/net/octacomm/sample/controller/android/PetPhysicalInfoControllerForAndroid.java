package net.octacomm.sample.controller.android;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.dao.mapper.RealTimeWeightMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.RealTimeWeight;

@RequestMapping("/android/pet/physical")
@Controller
public class PetPhysicalInfoControllerForAndroid {

	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;

	@Autowired
	private PetMapper petMapper;
	
	@Autowired private RealTimeWeightMapper realTimeWeightMapper; 
	
	@Autowired private DeviceMapper deviceMapper;

	/*@ResponseBody
	@RequestMapping(value = "/regist.do", method = RequestMethod.POST)
	public int regist(@RequestParam("petIdx") int petIdx, @RequestBody PetPhysicalInfo petPhysicalInfo) {
		petPhysicalInfoMapper.insert(petPhysicalInfo);
		return petMapper.registPhysical(petPhysicalInfo.getId(), petIdx);
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/regist.do" , method = RequestMethod.POST)
	public CommonResponce<Integer> regist(@RequestParam("petIdx") int petIdx, @RequestParam("groupCode") String groupCode,  @RequestBody PetPhysicalInfo petPhysicalInfo) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		
		addRealTimeWeight(petIdx, petPhysicalInfo.getWeight(), groupCode);
		petPhysicalInfoMapper.insert(petPhysicalInfo);
		int obj = petMapper.registPhysical(petPhysicalInfo.getId(), petIdx);
		if(obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
	}
	
	

	public int addRealTimeWeight(int petIdx, String weight, String groupCode) {
		Device device = deviceMapper.getDeviceInfoByGroupCode(groupCode);
		if ( device == null )
			return 0;
		RealTimeWeight realTimeWeight = new RealTimeWeight();
		realTimeWeight.setValue(Float.parseFloat(weight));
		realTimeWeight.setMac(device.getSerialNumber());
		realTimeWeight.setType(0);
		realTimeWeight.setTag(String.valueOf(petIdx));
		return realTimeWeightMapper.insert(realTimeWeight);
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public int delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		petMapper.resetPhysical(petIdx);
		return petPhysicalInfoMapper.delete(id);
	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/delete.do" , method = RequestMethod.POST)
	public CommonResponce<Integer> delete(@RequestParam("petIdx") int petIdx, @RequestParam("id") int id) {
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		petMapper.resetPhysical(petIdx);
		int obj = petPhysicalInfoMapper.delete(id);
		if(obj > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
		return responce;
		
		
	}

	/*@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public PetPhysicalInfo getPetPhysicalInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return petPhysicalInfoMapper.getPetPhysicalInformation(groupCode, petIdx);
	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public CommonResponce<PetPhysicalInfo> getBasicInformation(@RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		CommonResponce<PetPhysicalInfo> responce = new CommonResponce<PetPhysicalInfo>();
		PetPhysicalInfo obj = petPhysicalInfoMapper.getPetPhysicalInformation(groupCode, petIdx);
		if(obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
	
		return responce;
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/info/check", method = RequestMethod.POST)
	public PetPhysicalInfo basicInfoCheck(@RequestParam("groupId") String groupId, @RequestParam("petId") int petId) {
		return petPhysicalInfoMapper.InfoCheck(groupId, petId);
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public CommonResponce<PetPhysicalInfo> updatePhysical(@RequestBody PetPhysicalInfo petPhysicalInfo) {
		CommonResponce<PetPhysicalInfo> responce = new CommonResponce<PetPhysicalInfo>();
		
		int result = petPhysicalInfoMapper.update(petPhysicalInfo);
		if ( result > 0 ) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain( petPhysicalInfoMapper.get(petPhysicalInfo.getId()) );
		}
		else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(null);
		}
	
		return responce;
	}

}
