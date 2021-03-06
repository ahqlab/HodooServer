package net.octacomm.sample.domain;

import java.io.Serializable;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;

@Data
public class CommonResponce<D> implements Domain {
	
	public ResultMessage resultMessage;
	
	public D domain;
	
	public int status;
	
	public String error;
	
	public String exception;
	
	public String message;
	
	public String path;
	
}
