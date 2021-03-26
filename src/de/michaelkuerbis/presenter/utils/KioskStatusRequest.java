package de.michaelkuerbis.presenter.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import su.litvak.chromecast.api.v2.Request;

public class KioskStatusRequest implements Request {
	
	@JsonProperty
    boolean status;
	
    private Long requestId;
    
    public KioskStatusRequest(){
    	status = true;
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
