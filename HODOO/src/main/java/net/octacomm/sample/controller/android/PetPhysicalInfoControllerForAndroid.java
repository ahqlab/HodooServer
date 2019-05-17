package net.octacomm.sample.controller.android;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.AlarmObjectMapper;
import net.octacomm.sample.dao.mapper.DeviceMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.dao.mapper.PetPhysicalInfoMapper;
import net.octacomm.sample.dao.mapper.RealTimeWeightMapper;
import net.octacomm.sample.dao.mapper.UserMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.Device;
import net.octacomm.sample.domain.Message;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetChronicDisease;
import net.octacomm.sample.domain.PetPhysicalInfo;
import net.octacomm.sample.domain.RealTimeWeight;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.utils.FcmUtil;

@RequestMapping("/android/pet/physical")
@Controller
public class PetPhysicalInfoControllerForAndroid {

	@Autowired
	private PetPhysicalInfoMapper petPhysicalInfoMapper;

	@Autowired
	private PetMapper petMapper;
	
	@Autowired private RealTimeWeightMapper realTimeWeightMapper; 
	
	@Autowired private DeviceMapper deviceMapper;
	
	@Autowired
	AlarmObjectMapper alarmObjectMapper;
	
	@Autowired
	UserMapper userMapper;

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
		
		Pet pet = petMapper.get(petIdx);
		if ( Float.parseFloat(pet.getFixWeight()) == 0 )
			petMapper.setFixWeight(petIdx, petPhysicalInfo.getWeight());
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
	public CommonResponce<PetPhysicalInfo> updatePhysical(@RequestParam("petIdx") int petIdx,@RequestParam("groupCode") String groupCode, @RequestBody PetPhysicalInfo petPhysicalInfo) {
		CommonResponce<PetPhysicalInfo> responce = new CommonResponce<PetPhysicalInfo>();
		
		Pet pet = petMapper.get(petIdx);
		if ( Float.parseFloat( pet.getFixWeight() ) == 0 )
			petMapper.setFixWeight(petIdx, petPhysicalInfo.getWeight());
		
		int result = petPhysicalInfoMapper.update(petPhysicalInfo);
		addRealTimeWeight(petIdx, petPhysicalInfo.getWeight(), groupCode);
		if ( result > 0 ) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain( petPhysicalInfoMapper.get(petPhysicalInfo.getId()) );
			RealTimeThred thred = new RealTimeThred(groupCode, Float.parseFloat(petPhysicalInfo.getWeight()));
			thred.start();
		}
		else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(null);
		}
	
		return responce;
	}
	
	public class RealTimeThred extends Thread {
		private String userGroupCode;
		private float value;
		RealTimeThred ( String userGroupCode, float value ) {
			this.userGroupCode = userGroupCode;
			this.value = value;
		}
		@Override
		public void run() {
			super.run();
			List<User> userList = userMapper.getGroupMemner(userGroupCode);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < userList.size(); i++) {
				if ( userList.get(i).getPushToken() != null ) {
					
					int number = alarmObjectMapper.getAlarm(userList.get(i).getUserIdx());
					if ( number != 1 && (number & (0x01 << HodooConstant.WEIGNT_ALARM)) == 0 )
						continue;
					Message message = new Message();
					message.setTo( userList.get(i).getPushToken() );
					Map<String, Object> data = new HashMap<>();
					data.put("notiType", HodooConstant.FIREBASE_WEIGHT_TYPE);
					data.put("title", "체중감지");
					data.put("content", "새로운 체중이 감지되었습니다. 측정체중 : " + value + "kg");

					message.setData(data);
					
					FcmUtil.requestFCM(message);
				}
				
			}
		}
		
	}

}
