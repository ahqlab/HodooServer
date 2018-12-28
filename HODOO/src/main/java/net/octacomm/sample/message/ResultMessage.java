package net.octacomm.sample.message;

import lombok.Getter;
import lombok.Setter;

public enum ResultMessage
{
	//공통 ERROR MESSAGE
    FAILED(400),
    SUCCESS(200),  
    //USER 관련 ERROR MESSAGE
    NOT_FOUND_EMAIL(210),
    ID_PASSWORD_DO_NOT_MATCH(211),
	DUPLICATE_EMAIL(212),
	DUPLICATE_DEVICE(300);
	
	@Getter
    private final int code;

    private ResultMessage(int code)
    {
        this.code = code;
    }
    
}