package de.michaelkuerbis.presenter.rest;

import java.io.IOException;
import java.net.ConnectException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.michaelkuerbis.presenter.utils.KioskStatusRequest;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCastSpontaneousEvent;
import su.litvak.chromecast.api.v2.ChromeCastSpontaneousEventListener;

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
				cast.send("urn:x-cast:de.michaelkuerbis.kiosk",new KioskStatusRequest());
				
				String url = "";
				int refresh = 0;
				boolean fetched = false;
				
				ChromeCastSpontaneousEventListener listener = new ChromeCastSpontaneousEventListener(){

					@Override
					public void spontaneousEventReceived(ChromeCastSpontaneousEvent arg0) {
						log.debug("received event: "+arg0.getData());
						JSONParser parser = new JSONParser();
						String data = arg0.getData().toString().replaceFirst("AppEvent", "");
						data = data.replace("namespace: urn:x-cast:de.michaelkuerbis.kiosk,", "");
						data = data.replace("message", "\"message\"");
						try {
							JSONObject json = (JSONObject) parser.parse(data);
							JSONObject message = (JSONObject) json.get("message");
							status.put("url", message.get("url"));
							status.put("refresh", message.get("refresh"));
						} catch (ParseException e) {
							log.error("failed to parse json: "+data);
							e.printStackTrace();
						}
						status.put("fetched", true);
						
					}
					
				};
				
				cast.registerListener(listener);
				
				for(int i=0 ; i<50 && !status.containsKey("fetched");i++){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}				
				
				cast.unregisterListener(listener);
				
				
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
