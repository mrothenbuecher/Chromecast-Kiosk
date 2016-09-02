package de.michaelkuerbis.presenter.utils;

public class CastConnection {

	private String ip, name;
	private boolean isDefault = true;

	public CastConnection(String ip, String name){
		this.ip = ip;
		this.name = name;
	}
	
	public CastConnection(String ip, String name, boolean isDefault){
		this.ip = ip;
		this.name = name;
		this.isDefault = isDefault;
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


	public boolean isDefault() {
		return isDefault;
	}


	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
}
