package net.octacomm.sample.constant;

public class HodooConstant {

	public static final int INVITATION_RESET_TYPE = 0;
	public static final int ACCEPT_TYPE = 1;
	public static final int DECLINE_TYPE = 2;

	public static final int FIREBASE_NORMAL_TYPE = 0;
	public static final int FIREBASE_WEIGHT_TYPE = 1;
	public static final int FIREBASE_FEED_TYPE = 2;
	public static final int FIREBASE_INVITATION_TYPE = 3;
	public static final int FIREBASE_INVITATION_ACCEPT = 4;

	public static final int WITHDRAW = -1;

	public static final int KO_CODE = 1;
	public static final int EN_CODE = 2;
	public static final int JA_CODE = 3;
	public static final int CH_CODE = 4;

	public static final int FAIL_CODE = -1;
	public static final int MEMBER_EXIST = -2;
	public static final int SUCCESS_CODE = 1;
	public static final int NOT_GROUP_MASTER = 2;

	public static final int GROUP_NORMAL_MEMBER = 0;
	public static final int GROUP_MASTER_MEMBER = 1;
	
	/* 그룹 초대 관련 (s) */
	public static int NOT_TO_DEVICE = -2;
	public static int NOT_TO_USER = -1;
	public static int ERROR = 0;
	
	public static int EXISTENCE_USER = 2;
	public static int OVERLAB_INVITATION = 3;
	/* 그룹 초대 관련 (e) */

	/* pet 관련 등록 여부 체크 (s) */
	public static final String PET_REGIST_RESULT_KEY = "PET_REGIST_RESULT_KEY";
	public static final String PET_IDX_KEY = "PET_IDX_KEY";

	public static final int PET_REGIST_SUCESS = 1;
	public static final int PET_NOT_REGIST_PET = 0;
	public static final int PET_REGIST_FAILED = -1;
	public static final int PET_NOT_REGIST_DISEASES = -2;
	public static final int PET_NOT_REGIST_PHYSICAL = -3;
	public static final int PET_NOT_REGIST_WEIGHT = -4;
	/* pet 관련 등록 여부 체크 (s) */

	public static final String EMAIL_FROM_ADDRESS = "hellomyhodoo@gmail.com";

	public static final String FCM_APIKEY = "AAAAfhtaYsk:APA91bEgKSbdUUKWISstd-k2uDvzCla8anBmDQhibr114NYN7tfpwTI8QTaqamqZSpPwa2746TVIuUYlVGqGbUIH6oUjHI9zz6pzwDdvMt4yPmw492zfc6sAaJpAmukLO8B4fJngr4D_";

	/* SERVER RESPONSE */

	public static final int NO_CONTENT_RESPONSE = 204;
	public static final int OK_RESPONSE = 200;
	public static final int BAD_REQUEST = 400;
	public static final int SERVER_ERROR = 500;
	public static final int SQL_ERROR_RESPONSE = 500;
	
	
	/* ALARM */
	public static final int ALL_ALARM = 0;
	public static final int FEED_ALARM = 1;
	public static final int WEIGNT_ALARM = 2;
	public static final int GROUP_ALARM = 3;
	
}
