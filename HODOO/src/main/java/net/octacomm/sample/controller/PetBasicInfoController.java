package net.octacomm.sample.controller;

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

import net.octacomm.sample.dao.mapper.GroupPetMappingMapper;
import net.octacomm.sample.dao.mapper.PetBasicInfoMapper;
import net.octacomm.sample.dao.mapper.PetMapper;
import net.octacomm.sample.domain.GroupPetMapping;
import net.octacomm.sample.domain.Pet;
import net.octacomm.sample.domain.PetAllInfos;
import net.octacomm.sample.domain.PetBasicInfo;
import net.octacomm.sample.domain.ResultMessageGroup;
import net.octacomm.sample.message.ResultMessage;
import net.octacomm.sample.utils.MathUtil;

@RequestMapping("/pet")
@Controller
public class PetBasicInfoController {

	@Autowired
	private PetBasicInfoMapper petBasicInfoMapper;

	@Autowired
	private PetMapper petMapper;

	@ResponseBody
	@RequestMapping(value = "/basic/regist", method = RequestMethod.POST)
	public ResultMessageGroup regist(HttpServletRequest request, PetBasicInfo basicInfo) {
		ResultMessageGroup group = new ResultMessageGroup();
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
				basicInfo.setProfileFilePath(
						"/upload/profile/" + randomeUUID + "_" + basicInfo.getProfile().getOriginalFilename());
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
		pet.setPetGroupCode(basicInfo.getGroupCode());
		pet.setBasic(basicInfo.getId());
		pet.setDisease(0);
		pet.setPhysical(0);
		pet.setWeight(0);
		int result = petMapper.insert(pet);
		if (result != 0) {
			group.setResultMessage(ResultMessage.SUCCESS);
			group.setDomain(pet);
		} else {
			group.setResultMessage(ResultMessage.FAILED);
			group.setDomain(null);
		}
		return group;
	}

	@ResponseBody
	@RequestMapping(value = "/basic/update", method = RequestMethod.POST)
	public ResultMessageGroup update(HttpServletRequest request, PetBasicInfo basicInfo) {
		ResultMessageGroup group = new ResultMessageGroup();
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
				basicInfo.setProfileFilePath(
						"/upload/profile/" + randomeUUID + "_" + basicInfo.getProfile().getOriginalFilename());
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
	@RequestMapping(value = "/basic/get", method = RequestMethod.POST)
	public PetBasicInfo getBasicInformation(HttpServletRequest request, @RequestParam("groupCode") String groupCode, @RequestParam("petIdx") int petIdx) {
		return petBasicInfoMapper.getBasicInformation(groupCode, petIdx);
	}

	@ResponseBody
	@RequestMapping(value = "/about/my/pet/list", method = RequestMethod.POST)
	public List<PetAllInfos> aboutMyPetList(@RequestParam("groupCode") String groupCode) {
		return petMapper.aboutMyPetList(groupCode);
	}

}
