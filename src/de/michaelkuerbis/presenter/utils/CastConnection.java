package de.michaelkuerbis.presenter.utils;

public class CastConnection {

	private String ip, name;

	public CastConnection(String ip, String name){
		this.ip = ip;
		this.name = name;
	}
	
	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
