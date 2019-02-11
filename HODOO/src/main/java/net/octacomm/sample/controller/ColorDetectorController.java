package net.octacomm.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.octacomm.sample.dao.mapper.StandardHsvMapper;
import net.octacomm.sample.domain.HsvValue;
import net.octacomm.sample.domain.StandardHsv;

@Controller
@RequestMapping("/color")
public class ColorDetectorController {

	@Autowired
	private StandardHsvMapper standardHsvMapper;

	@ResponseBody
	@RequestMapping(value = "/detector.do", method = RequestMethod.POST)
	public HsvValue detector(@RequestBody HsvValue hsv) {
		System.err.println("hsv : " +hsv);
		HsvValue result = new HsvValue();
		StandardHsv SG = standardHsvMapper.get(1);
		int sg = getFindNearH(SG, hsv.getSg());
		result.setSg(String.valueOf(sg));
		
		StandardHsv PH = standardHsvMapper.get(2);
		int ph = getFindNearH(PH, hsv.getPh());
		result.setPh(String.valueOf(ph));
		
		StandardHsv LEU = standardHsvMapper.get(3);
		int leu = getFindNearH(LEU, hsv.getLeu());
		result.setLeu(String.valueOf(leu));
		
		StandardHsv NIT = standardHsvMapper.get(4);
		int nit = getFindNearH(NIT, hsv.getNit());
		result.setNit(String.valueOf(nit));
		
		StandardHsv PRO = standardHsvMapper.get(5);
		int pro = getFindNearH(PRO, hsv.getPro());
		result.setPro(String.valueOf(pro));
		
		StandardHsv GLU = standardHsvMapper.get(6);
		int glu = getFindNearH(GLU, hsv.getGlu());
		result.setGlu(String.valueOf(glu));
		
		StandardHsv KET = standardHsvMapper.get(7);
		int ket = getFindNearH(KET, hsv.getKet());
		result.setKet(String.valueOf(ket));
		
		StandardHsv UBG = standardHsvMapper.get(8);
		int ubg = getFindNearV(UBG, hsv.getUbg());
		result.setUbg(String.valueOf(ubg));
		
		StandardHsv BIL = standardHsvMapper.get(9);
		int bil = getFindNearH(BIL, hsv.getBil());
		result.setBil(String.valueOf(bil));
		
		StandardHsv ERY = standardHsvMapper.get(10);
		int ery = getFindNearV(ERY, hsv.getEry());
		result.setEry(String.valueOf(ery));
		
		StandardHsv HB = standardHsvMapper.get(11);
		int hb = getFindNearH(HB, hsv.getHb());
		result.setHb(String.valueOf(hb));
		
		return result;
	}

	public Integer getFindNearH(StandardHsv HSV, String value) {
		int colors[] = { strSpriterOfH(HSV.getLevel1()), strSpriterOfH(HSV.getLevel2()), strSpriterOfH(HSV.getLevel3()),
				strSpriterOfH(HSV.getLevel4()), strSpriterOfH(HSV.getLevel5()), strSpriterOfH(HSV.getLevel6()),
				strSpriterOfH(HSV.getLevel7()) };
		int sg = getNear(colors, strSpriterOfH(value));
		return getIndexOf(sg, colors);
	}
	
	public Integer getFindNearV(StandardHsv HSV, String value) {
		int colors[] = { strSpriterOfV(HSV.getLevel1()), strSpriterOfV(HSV.getLevel2()), strSpriterOfV(HSV.getLevel3()),
				strSpriterOfV(HSV.getLevel4()), strSpriterOfV(HSV.getLevel5()), strSpriterOfV(HSV.getLevel6()),
				strSpriterOfV(HSV.getLevel7()) };
		int sg = getNear(colors, strSpriterOfV(value));
		return getIndexOf(sg, colors);
	}
	public static int getIndexOf(int toSearch, int[] tab) {
		int i = 0;
		while (!(tab[i] == toSearch)) {
			i++;
		}
		return i; // or return tab[i];
	}

	private Integer strSpriterOfH(String str) {
		String[] array = str.split("/");
		return Integer.parseInt(array[0]);
	}
	
	private Integer strSpriterOfV(String str) {
		String[] array = str.split("/");
		return Integer.parseInt(array[2]);
	}

	private int getNear(int[] data, int near) {
		int min = Integer.MAX_VALUE; // 기준데이터 최소값 - Interger형의 최대값으로 값을 넣는다.
		int nearData = 0; // 가까운 값을 저장할 변수
		// 2. process
		for (int i = 0; i < data.length; i++) {
			int a = Math.abs(data[i] - near); // 절대값을 취한다.
			if (min > a) {
				min = a;
				nearData = data[i];
			}
		}
		// 3. 출력
		System.out.println(near + "에 근접한 값 : " + nearData);
		return nearData;
	}
}
