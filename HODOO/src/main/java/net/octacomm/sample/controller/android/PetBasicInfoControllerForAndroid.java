package net.octacomm.sample.controller.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.octacomm.sample.constant.HodooConstant;
import net.octacomm.sample.dao.mapper.GroupPetMappingMapper;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetBreedMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.domain.CommonResponce;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/android/pet")
@Controller
public class PetBasicInfoControllerForAndroid {

	@Autowired
	private PetBasicInfoMapper petBasicInfoMapper;

	@Autowired
	private PetMapper petMapper;

	@Autowired
	PetBreedMapper breedMapper;

	@ResponseBody
	@RequestMapping(value = "/basic/regist.do", method = RequestMethod.POST)
	public CommonResponce<Pet> regist(HttpServletRequest request, PetBasicInfo basicInfo) {
		System.err.println("basicInfo : " + basicInfo);
		CommonResponce<Pet> group = new CommonResponce<Pet>();
		String localPath = "/resources/upload/profile/";
		String path = request.getSession().getServletContext().getRealPath(localPath);
		System.err.println("path : " + path);
		UUID randomeUUID = UUID.randomUUID();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String organizedfilePath = null;
		if (basicInfo.getProfile().getSize() > 0) {
			try {
				inputStream = basicInfo.getProfile().getInputStream();
				File realUploadDir = new File(path);
				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();
				}
				organizedfilePath = path + "/" + randomeUUID + "_" + basicInfo.getProfile().getOriginalFilename();
				System.err.println("organizedfilePath : " + organizedfilePath);
				basicInfo.setProfileFilePath("/resources/upload/profile/" + randomeUUID + "_"
						+ basicInfo.getProfile().getOriginalFilename());
				basicInfo.setProfileFileName(basicInfo.getProfile().getOriginalFilename());
				outputStream = new FileOutputStream(organizedfilePath);
				int readByte = 0;
				byte[] buffer = new byte[16384];

				while ((readByte = inputStream.read(buffer, 0, buffer.length)) != -1) {
					outputStream.write(buffer, 0, readByte);
				}
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		petBasicInfoMapper.insert(basicInfo);
		Pet pet = new Pet();
		pet.setBasic(basicInfo.getId());
		pet.setDisease(0);
		pet.setPhysical(0);
		pet.setWeight(0);
		int result = petMapper.insert(pet);
		if (result != 0) {
			group.setResultMessage(ResultMessage.SUCCESS);
			group.setDomain(pet);

			int breedCount = breedMapper.getBreedMapperCount(basicInfo.getId());
			if (breedCount > 0) {
				breedMapper.updatePetBreedMapper(basicInfo.getId(), Integer.parseInt(basicInfo.getPetBreed()));
			} else {
				breedMapper.insertPetBreedMapper(basicInfo.getId(), Integer.parseInt(basicInfo.getPetBreed()));
			}

		} else {
			group.setResultMessage(ResultMessage.FAILED);
			group.setDomain(null);
		}
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/basic/update.do", method = RequestMethod.POST)
	public CommonResponce<Integer> update(HttpServletRequest request, PetBasicInfo basicInfo) {
		CommonResponce<Integer> group = new CommonResponce<Integer>();
		String localPath = "/resources/upload/profile/";
		String path = request.getSession().getServletContext().getRealPath(localPath);
		System.err.println("path : " + path);

		int breedCount = breedMapper.getBreedMapperCount(basicInfo.getId());
		if (breedCount > 0) {
			breedMapper.updatePetBreedMapper(basicInfo.getId(), Integer.parseInt(basicInfo.getPetBreed()));
		} else {
			breedMapper.insertPetBreedMapper(basicInfo.getId(), Integer.parseInt(basicInfo.getPetBreed()));
		}

		UUID randomeUUID = UUID.randomUUID();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String organizedfilePath = null;
		if (basicInfo.getProfile().getSize() > 0) {
			try {
				inputStream = basicInfo.getProfile().getInputStream();
				File realUploadDir = new File(path);
				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();
				}
				organizedfilePath = path + "/" + randomeUUID + "_" + basicInfo.getProfile().getOriginalFilename();
				System.err.println("organizedfilePath : " + organizedfilePath);
				basicInfo.setProfileFilePath("/resources/upload/profile/" + randomeUUID + "_"
						+ basicInfo.getProfile().getOriginalFilename());
				basicInfo.setProfileFileName(basicInfo.getProfile().getOriginalFilename());
				outputStream = new FileOutputStream(organizedfilePath);
				int readByte = 0;
				byte[] buffer = new byte[16384];

				while ((readByte = inputStream.read(buffer, 0, buffer.length)) != -1) {
					outputStream.write(buffer, 0, readByte);
				}
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		int result = petBasicInfoMapper.update(basicInfo);
		if (result != 0) {
			group.setResultMessage(ResultMessage.SUCCESS);
			group.setDomain(basicInfo.getId());
		} else {
			group.setResultMessage(ResultMessage.FAILED);
			group.setDomain(basicInfo.getId());
		}
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/basic/get.do", method = RequestMethod.POST)
	public CommonResponce<PetBasicInfo> getBasicInformation(HttpServletRequest request,
			@RequestParam("location") String location, @RequestParam("groupCode") String groupCode,
			@RequestParam("petIdx") int petIdx) {
		// PetBasicInfo info = petBasicInfoMapper.getBasicInformation(location,
		// groupCode, petIdx);
		CommonResponce<PetBasicInfo> responce = new CommonResponce<PetBasicInfo>();
		PetBasicInfo obj = petBasicInfoMapper.getBasicInformation(location, groupCode, petIdx);
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
	@RequestMapping(value = "/about/my/pet/list.do")
	public CommonResponce<List<PetAllInfos>> aboutMyPetList(@RequestParam("groupCode") String groupCode) {
		CommonResponce<List<PetAllInfos>> responce = new CommonResponce<List<PetAllInfos>>();
		List<PetAllInfos> list = petMapper.aboutMyPetList(groupCode);
		for (PetAllInfos petAllInfos : list) {
			petAllInfos.getPetBasicInfo().setCurrentMonth(petAllInfos.getPetBasicInfo().currentMonth());
			petAllInfos.getPetBasicInfo().setCurrentYear(petAllInfos.getPetBasicInfo().currentYear());
		}
		if(list.size() > 0) {
			responce.setStatus(HodooConstant.OK_RESPONSE);
			responce.setDomain(list);
		}else {
			responce.setStatus(HodooConstant.NO_CONTENT_RESPONSE);
			responce.setDomain(list);
		}
		return responce;
	}

	@ResponseBody
	@RequestMapping(value = "/test/image/upload.do", method = RequestMethod.POST)
	public CommonResponce<User> IosMultiPartTest(@RequestParam("profile") MultipartFile profile) {
		System.err.println("profile : " + profile.getOriginalFilename());
		CommonResponce<User> responce = new CommonResponce<User>();
		responce.setResultMessage(ResultMessage.SUCCESS);
		return responce;
	}

}
