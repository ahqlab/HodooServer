<<<<<<< HEAD
package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class RealTimeWeight implements Domain{
	
	public RealTimeWeight() {
		
	}
	
	public RealTimeWeight(String mac, float value) {
		this.mac = mac;
		this.value = value;
	}

	private int id;
	
	private String mac;
	
	private float value;
	
	private String type;
	
	private String tag;
	
	private String createDate;

=======
package net.octacomm.sample.domain;

import lombok.Data;

@Data
public class RealTimeWeight implements Domain{
	
	public RealTimeWeight() {
		
	}
	
	public RealTimeWeight(String mac, float value) {
		this.mac = mac;
		this.value = value;
	}

	private int id;
	
	private String mac;
	
	private float value;
	
	private String type;
	
	private String createDate;

>>>>>>> branch 'master' of https://github.com/ahqlab/HodooServer.git
}