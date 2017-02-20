package de.michaelkuerbis.presenter.rest;

import java.io.IOException;
import java.net.ConnectException;
import java.security.GeneralSecurityException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import de.michaelkuerbis.presenter.utils.KioskStatusRequest;
import de.michaelkuerbis.presenter.utils.KioskStatusResponse;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;

@Path("/status")
public class StatusREST {
	
	private final static Logger log = LogManager.getLogger(StatusREST.class);
	
	@GET
	@Path("/get/{ip}")
	public Response getStatus(@PathParam("ip") String ip) {
		final JSONObject status = new JSONObject();
		log.debug("request status for ip: "+ip);
		status.put("ip", ip);
		ChromeCast cast = new ChromeCast(ip);
		try {
			cast.connect();
			status.put("status", "online");
			status.put("application", cast.getRunningApp().name);
			log.debug("cast "+ip+" connected application: "+cast.getRunningApp().name);
			if(cast.getRunningApp().name.equals("chromecast-kiosk-web")){
				Application cation = cast.getRunningApp();
				KioskStatusResponse resp = cast.send("urn:x-cast:de.michaelkuerbis.kiosk",new KioskStatusRequest(), KioskStatusResponse.class);
				
				if(resp!=null){
					status.put("url", resp.getUrl());
					status.put("refresh", resp.getRefresh());
					status.put("fetched", true);
				}else{
					status.put("fetched", false);
				}
				
				
			}
			cast.disconnect();
		} catch (IOException e) {
			status.put("status", "offline");
			if (e instanceof ConnectException) {
				status.put("reason", "3");
				log.error("cast "+ip+" not connected reason: "+e.getMessage());
			} else{
				status.put("reason", "1");
				log.error("cast "+ip+" not connected reason: "+e.getMessage());
			}
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			status.put("status", "offline");
			status.put("reason", "2");
			log.error("cast "+ip+" not connected reason: "+e.getMessage());
			e.printStackTrace();
		}
		return Response.ok(status.toJSONString()).build();
	}

}
