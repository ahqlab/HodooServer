package net.octacomm.sample.domain.weather;

@lombok.Data
public class Data {
	
	private String timestamp_local;
	private String timestamp_utc;
	private String ts;
	private String datetime;
	private float wind_gust_spd;
	private float wind_spd;
	private float wind_dir;
	private float uv;
	private float ozone;
	private float temp;
	
	private Weather weather;
	
}
