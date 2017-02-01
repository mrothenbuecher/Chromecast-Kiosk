package de.michaelkuerbis.presenter.rest;

import java.io.IOException;
import java.net.ConnectException;
import java.security.GeneralSecurityException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import de.michaelkuerbis.presenter.utils.KioskStatusRequest;
import de.michaelkuerbis.presenter.utils.KioskStatusResponse;
import su.litvak.chromecast.api.v2.ChromeCast;

@Path("/status")
public class StatusREST {
	
	@GET
	@Path("/get/{ip}")
	public Response getStatus(@PathParam("ip") String ip) {
		JSONObject status = new JSONObject();
		status.put("ip", ip);
		ChromeCast cast = new ChromeCast(ip);
		try {
			cast.connect();
			status.put("status", "online");
			status.put("application", cast.getRunningApp().name);
			if(cast.getRunningApp().name.equals("chromecast-kiosk-web")){
				KioskStatusResponse resp = cast.send("urn:x-cast:de.michaelkuerbis.kiosk",new KioskStatusRequest(), KioskStatusResponse.class);
				if(resp != null){
					System.out.println("URL: "+resp.getUrl());
					System.out.println("Refresh: "+resp.getRefresh());
				}else{
					System.out.println("no Payload");
				}
			}
			cast.disconnect();
		} catch (IOException e) {
			status.put("status", "offline");
			if (e instanceof ConnectException) {
				status.put("reason", "3");
			} else
				status.put("reason", "1");
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			status.put("status", "offline");
			status.put("reason", "2");
			e.printStackTrace();
		}
		return Response.ok(status.toJSONString()).build();
	}

}
