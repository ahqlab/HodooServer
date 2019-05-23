package net.octacomm.sample.controller.android;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetBreedMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.HodooIndex;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.PetBreed;
import net.octacomm.sample.domain.PetPhysicalInfo;


@RequestMapping("/android/pet")
@Controller
public class PetControllerForAndroid {
	
	@Autowired
	private PetBasicInfoMapper petBasicInfoMapper;

	@Autowired
	private PetMapper petMapper;
	
	@Autowired
	private PetBreedMapper breedMapper;
	
	
	/*@ResponseBody
	@RequestMapping(value = "/my/list.do", method = RequestMethod.POST)
	public List<PetBasicInfo> myList(HttpServletRequest request, @RequestParam("groupId") int groupId) {
		return  petBasicInfoMapper.getMyPetList(groupId);
	}*/
	
	/*@ResponseBody
	@RequestMapping(value = "/my/registered/list.do", method = RequestMethod.POST)
	public List<PetBasicInfo> getMyRegisteredPetList(HttpServletRequest request, @RequestParam("groupId") String groupId) {
		return  petBasicInfoMapper.getMyRegisteredPetList(groupId);
	}*/
	
	/*@ResponseBody
	@RequestMapping(value = "/basic/info/get.do", method = RequestMethod.POST)
	public PetBasicInfo login(HttpServletRequest request, @RequestParam("id") int id) {
		return petBasicInfoMapper.getBasicInfoForPetId(id);
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/my/pet/list.do", method = RequestMethod.POST)
	public CommonResponce<List<Pet>> myPetList(@RequestParam("groupCode") String groupCode){
		CommonResponce<List<Pet>> responce = new CommonResponce<List<Pet>>();
		List<Pet> list = petMapper.myPetList(groupCode);
		if(list.size() > 0) {
			responce.setDomain(list);
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setDomain(list);
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/exist/my/pet.do", method = RequestMethod.POST)
	public CommonResponce<Integer> existMyPet(@RequestParam("groupCode") String groupCode){
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		int result = petMapper.myRegistedPetCount(groupCode);
		if(result > 0) {
			responce.setDomain(result);
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setDomain(result);
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/my/pet/listResult.do", method = RequestMethod.POST)
	public int[] myPetListResult(@RequestParam("groupCode") String groupCode){
		List<Pet> pets = petMapper.myPetList(groupCode);
		int resultCode = HodooConstant.PET_REGIST_FAILED;
		if ( !pets.isEmpty() ) {
			if ( pets.size() == 1 ) {
				if ( pets.get(0).getBasic() == 0 )
					resultCode = HodooConstant.PET_REGIST_FAILED;
				else if ( pets.get(0).getDisease() == 0 )
					resultCode = HodooConstant.PET_NOT_REGIST_DISEASES;
				else if ( pets.get(0).getPhysical() == 0 )
					resultCode = HodooConstant.PET_NOT_REGIST_PHYSICAL;
				else if ( pets.get(0).getWeight() == 0 )
					resultCode = HodooConstant.PET_NOT_REGIST_WEIGHT;
				else 
					resultCode = HodooConstant.PET_REGIST_SUCESS;
			} else {
				resultCode = HodooConstant.PET_REGIST_SUCESS;
			}
		}
		
		int result[] = new int[1];
		
		switch (resultCode) {
		case HodooConstant.PET_REGIST_SUCESS:
		case HodooConstant.PET_REGIST_FAILED:
			result[0] = resultCode;
			break;
		default:
			result = new int[2];
			result[0] = resultCode;
			result[1] = pets.get(0).getPetIdx();
			break;
		}
		return result;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/all/infos.do", method = RequestMethod.POST)
	public CommonResponce<PetAllInfos> petAllInfos(@RequestParam("petIdx") int petIdx){
		CommonResponce<PetAllInfos> responce = new CommonResponce<PetAllInfos>();
		PetAllInfos obj = petMapper.allInfoOnThePet(petIdx);
		if(obj != null) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(obj);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(obj);
		}
	
		return responce;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/all/getBreed.do", method = RequestMethod.POST)
	public CommonResponce<List<PetBreed>> getAllBreed( @RequestParam("location") String location ){
		CommonResponce<List<PetBreed>> responce = new CommonResponce<List<PetBreed>>();
		List<PetBreed> list = breedMapper.getAllList( location );
		if(list.size() > 0) {
			responce.setDomain(list);
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setDomain(list);
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/breed/of/type.do", method = RequestMethod.POST)
	public CommonResponce<List<PetBreed>> getAllBreedOfType( @RequestParam("location") String location, @RequestParam("typeIdx") int typeIdx ){
		CommonResponce<List<PetBreed>> responce = new CommonResponce<List<PetBreed>>();
		List<PetBreed> list = breedMapper.getAllBreedOfType( location, typeIdx );
		if(list.size() > 0) {
			responce.setDomain(list);
			responce.setStatus(HodooConstant.OK_RESPONSE);
		}else {
			responce.setDomain(list);
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
		}
		return responce;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/make/it/invisible.do", method = RequestMethod.POST)
	public CommonResponce<Integer> makeItInvisible( @RequestParam("petIdx") int petIdx ){
		CommonResponce<Integer> responce = new CommonResponce<Integer>();
		responce.setDomain(petMapper.makeItInvisible(petIdx));
		responce.setStatus(200);
		return responce;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/get/weight/standard/and/hodoo/index.do", method = RequestMethod.POST)
	public CommonResponce<HodooIndex> getWeightStandardAndHodooIndex( @RequestParam("petIdx") int petIdx , @RequestParam("location") String location ){
		CommonResponce<HodooIndex> responce = new CommonResponce<HodooIndex>();
		PetAllInfos info = petMapper.allInfoOnThePet(petIdx);
		responce.setDomain(getWeightStd(info, location));
		responce.setStatus(200);
		return responce;
	}
	
	
	
	public HodooIndex getWeightStd(PetAllInfos info, String location) {
		System.err.println(info.getPetBasicInfo().getCurrentMonth());
		if(info.getPetBasicInfo().getCurrentMonth() < 12){
			return weigtPuppy(info, location);
		}else {
			return weigtAdult(info, location);
		}
	}
	
	public HodooIndex weigtAdult(PetAllInfos info, String location) {
		HodooIndex hodoo = new HodooIndex();
		int factorWeight = 1;
		int factorBelly = 1;
		int factorRib = 1;
		
		String[] word = info.getPetBasicInfo().getSelectedBfi().split(",");
		System.err.println("word[0] : " + word[0]);
		System.err.println("word[1] : " + word[1]);
		System.err.println("word[2] : " + word[2]);
		System.err.println("info.getPet().getFixWeight() : " + info.getPet().getFixWeight());
		int scoreWeist = Integer.parseInt(word[0]);  int scoreBelly = Integer.parseInt(word[1]); int scoreRib = Integer.parseInt(word[2]);

		float indexHodoo = ((( factorWeight * scoreWeist ) + ( factorBelly * scoreBelly ) + ( factorRib * scoreRib)) / 3) - 1;
		hodoo.setHodooIndex(indexHodoo);
		if(indexHodoo == -1) {
			hodoo.setHodooIndexDesc("마름");
		}else if(indexHodoo == 0) {
			hodoo.setHodooIndexDesc("보통");
		}else if(indexHodoo == 1) {
			hodoo.setHodooIndexDesc("통통");
		}else if(indexHodoo == 2) {
			hodoo.setHodooIndexDesc("비만");
		}
		
		float factorLoss = 0;
		
		if(-1.0 <= indexHodoo && indexHodoo < 0.0) {
			factorLoss = (float) (0.05 * indexHodoo);
		} else {
			factorLoss = (float) (0.05 * Math.pow(indexHodoo, 2)) + (float) ( 0.05 * indexHodoo);
		}
		float weightStd = Float.parseFloat(info.getPet().getFixWeight()) - (Float.parseFloat(info.getPet().getFixWeight()) * factorLoss);
		float minweightStd = (float) (weightStd * 0.95);
		float maxweightStd = (float) (weightStd * 1.05);
		hodoo.setMinWeightStd(minweightStd);
		hodoo.setMaxWeightStd(maxweightStd);
		hodoo.setWeightStd(weightStd);
		return hodoo;
	}
	
	public HodooIndex weigtPuppy( PetAllInfos info , String location) {
		
		HodooIndex hodoo = new HodooIndex();
		int factorWeight = 1;
		int factorBelly = 1;
		int factorRib = 1;
		
		String[] word = info.getPetBasicInfo().getSelectedBfi().split(",");
		System.err.println("word[0] : " + word[0]);
		System.err.println("word[1] : " + word[1]);
		System.err.println("word[2] : " + word[2]);
		System.err.println("info.getPet().getFixWeight() : " + info.getPet().getFixWeight());
		int scoreWeist = Integer.parseInt(word[0]);  int scoreBelly = Integer.parseInt(word[1]); int scoreRib = Integer.parseInt(word[2]);

		float indexHodoo = ((( factorWeight * scoreWeist ) + ( factorBelly * scoreBelly ) + ( factorRib * scoreRib)) / 3) - 1;
		hodoo.setHodooIndex(indexHodoo);
		if(indexHodoo == -1) {
			hodoo.setHodooIndexDesc("마름");
		}else if(indexHodoo == 0) {
			hodoo.setHodooIndexDesc("보통");
		}else if(indexHodoo == 1) {
			hodoo.setHodooIndexDesc("통통");
		}else if(indexHodoo == 2) {
			hodoo.setHodooIndexDesc("비만");
		}
		
		float factorLoss = 0;
		
		if(-1.0 <= indexHodoo && indexHodoo < 0.0) {
			factorLoss = (float) (0.05 * indexHodoo);
		} else {
			factorLoss = (float) (0.05 * Math.pow(indexHodoo, 2)) + (float) ( 0.05 * indexHodoo);
		}
		float weightStd = Float.parseFloat(info.getPet().getFixWeight()) - (Float.parseFloat(info.getPet().getFixWeight()) * factorLoss);
		float minweightStd = (float) (weightStd * 0.95);
		float maxweightStd = (float) (weightStd * 1.05);
		hodoo.setMinWeightStd(minweightStd);
		hodoo.setMaxWeightStd(maxweightStd);
		hodoo.setWeightStd(weightStd);
		return hodoo;
	}
	
	public void standardWeight() {
		
	}
	
}
