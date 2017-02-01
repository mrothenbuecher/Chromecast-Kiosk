package de.michaelkuerbis.presenter.utils;

import org.codehaus.jackson.annotate.JsonProperty;

import su.litvak.chromecast.api.v2.Response;

public class KioskStatusResponse implements Response {

	//current url
	@JsonProperty("url")
	String url;
	//current refresh rate
    @JsonProperty("refresh")
    int refresh;
	
    Long requestId;
	
	public String getUrl(){
		return this.url;
	}
	
	public int getRefresh(){
		return this.refresh;
	}
	
	@Override
	public Long getRequestId() {
		return this.requestId;
	}

	@Override
	public void setRequestId(Long arg0) {
		this.requestId = arg0;
	}

}
