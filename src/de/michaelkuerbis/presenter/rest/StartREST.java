package de.michaelkuerbis.presenter.rest;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import de.michaelkuerbis.presenter.utils.KioskRequest;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.Status;

@Path("/start")
public class StartREST {

	private final String appId = "10B2AF08";

	@POST
	@Path("/{ip}")
	public Response addChromecast(@PathParam("ip") String ip,
			@FormParam("url") String url, @FormParam("reload") int reload) {

		ChromeCast chromecast = new ChromeCast(ip);
		try {
			chromecast.connect();
			if (chromecast.isConnected()) {
				Status status = chromecast.getStatus();
				if (chromecast.isAppAvailable(appId)) {
					Application app = chromecast.launchApp(appId);
					chromecast.send("urn:x-cast:de.michaelkuerbis.kiosk",
							new KioskRequest(url, reload));
					return Response.ok().build();
				}
				else return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("app is not aviable")
						.build();
			} else {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("chromecast did not react / ip of chromecast may wrong")
						.build();
			}
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}
