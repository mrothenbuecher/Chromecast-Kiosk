package de.michaelkuerbis.presenter.rest;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.MediaStatus;
import su.litvak.chromecast.api.v2.Status;
import de.michaelkuerbis.presenter.utils.KioskUpdateRequest;
import de.michaelkuerbis.presenter.utils.Settings;

@Path("/start")
public class StartREST {

	@POST
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{ip}")
	public Response startCast(@PathParam("ip") String ip,
			@QueryParam("url") String url, @QueryParam("reload") int reload) {

		ChromeCast chromecast = new ChromeCast(ip);
		try {
			chromecast.connect();
			if (chromecast.isConnected()) {
				Status status = chromecast.getStatus();
				if (chromecast.isAppAvailable(Settings.appId)) {
					Application app = chromecast.launchApp(Settings.appId);
					chromecast.send("urn:x-cast:de.michaelkuerbis.kiosk",
							new KioskUpdateRequest(url, reload));
					chromecast.disconnect();
					return Response.ok().build();
				}
				else {
					chromecast.disconnect();
					return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("app is not available")
						.build();
				}
			} else {
				return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("chromecast did not react / ip of chromecast may wrong")
						.build();
			}
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		
	}

	@POST
	@Path("/stream/{ip}")
	public Response startStream(@PathParam("ip") String ip, @FormParam("file") String url) {

		ChromeCast chromecast = new ChromeCast(ip);
		try {
			chromecast.connect();
			if (chromecast.isConnected()) {
				MediaStatus status = chromecast.load(url);
				return Response.ok().build();
			} else {
				return Response
						.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("chromecast did not react / ip of chromecast may wrong")
						.build();
			}
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		
	}
	
}
