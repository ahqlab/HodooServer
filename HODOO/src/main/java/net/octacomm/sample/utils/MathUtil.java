package net.octacomm.sample.utils;

import java.util.Random;

public class MathUtil {

	private static Random random = new Random();

	public static int random(int num) {
		return random.nextInt(num);
	}

	public static String getGroupId() {
		String tempPassword = "";
		for (int i = 0; i < 12; i++) {
			int rndVal = (int) (Math.random() * 62);
			if (rndVal < 10) {
				tempPassword += rndVal;
			} else if (rndVal > 35) {
				tempPassword += (char) (rndVal + 61);
			} else {
				tempPassword += (char) (rndVal + 55);
			}
		}
		//out.println("tempPassword : " + tempPassword);
		return tempPassword;
	}
}
