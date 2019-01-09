package net.octacomm.sample.domain;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;

@Data
public class CommonResponce<D extends Domain> implements Domain {
	
	public ResultMessage resultMessage;
	
	public D domain;
}
