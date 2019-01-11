package net.octacomm.sample.domain;

import java.util.List;

import lombok.Data;

@Data
public class ArrayListDevice {

	private List<Device> devices;

	public ArrayListDevice(List<Device> devices) {
		this.devices = devices;
	}

}
