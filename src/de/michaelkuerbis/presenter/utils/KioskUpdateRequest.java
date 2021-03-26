package de.michaelkuerbis.presenter.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import su.litvak.chromecast.api.v2.Request;

public class KioskUpdateRequest implements Request {

	@JsonProperty
    final String url;
    @JsonProperty
    final String type;
    @JsonProperty("refresh")
    final int refresh;
	
    private Long requestId;
    
    public KioskUpdateRequest(String url, int refresh){
    	this.url = url;
    	this.refresh = refresh;
    	this.type = "load";
    }
    
    @Override
    public Long getRequestId() {
        return requestId;
    }

    @Override
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

}
