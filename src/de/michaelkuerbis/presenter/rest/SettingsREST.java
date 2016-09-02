package de.michaelkuerbis.presenter.rest;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import de.michaelkuerbis.presenter.servlets.SettingsServlet;
import de.michaelkuerbis.presenter.utils.CastConnection;

@Path("/settings")
public class SettingsREST {

	@Context
	private HttpServletRequest webRequest;

	@POST
	@Path("/add/{ip}/{name}")
	public Response addChromecast(@PathParam("ip") String ip,
			@PathParam("name") String name) {
		JSONObject obj = new JSONObject();
		if (ip != null && !ip.isEmpty() && name != null && !name.isEmpty()) {
			boolean alreadyDefined = false;
			for (CastConnection con : SettingsServlet.getConnections()) {
				if (con.getIp().equals(ip)) {
					alreadyDefined = true;
					obj.put("error", "chromecast already defined with name: "
							+ con.getName());
					break;
				}
			}
			if (!alreadyDefined) {
				CastConnection con = new CastConnection(ip, name, true);
				Vector<CastConnection> vec = SettingsServlet.getConnections();
				vec.add(con);
				SettingsServlet.setConnections(vec);
				if (SettingsServlet.saveSettings(this.webRequest
						.getServletContext())) {
					obj.put("ok", "");
				} else {
					obj.put("error", "failed to save settings.");
				}
			}
		} else {
			obj.put("error", "ip and name must be defined.");
		}
		return Response.ok().entity(obj.toString()).build();
	}

	@POST
	@Path("/update/{ip}/{default}")
	public Response updateChromecast(@PathParam("ip") String ip,
			@PathParam("default") String isDefault) {
		JSONObject obj = new JSONObject();
		System.out.println(isDefault +" val:"+(isDefault != null && !isDefault.isEmpty()
				&& isDefault.toLowerCase().equals("true")));
		if (ip != null && !ip.isEmpty()) {
			Vector<CastConnection> vec = SettingsServlet.getConnections();
			for (int i = 0; i < vec.size(); i++) {
				CastConnection con = vec.get(i);
				if (con.getIp().equals(ip)) {
					con.setDefault(isDefault != null && !isDefault.isEmpty()
							&& isDefault.toLowerCase().equals("true"));
					vec.set(i, con);
					SettingsServlet.setConnections(vec);
					if (SettingsServlet.saveSettings(this.webRequest
							.getServletContext())) {
						obj.put("ok", "");
					} else {
						obj.put("error", "failed to save settings.");
					}
					break;
				}
			}
		} else {
			obj.put("error", "ip and name must be defined.");
		}
		return Response.ok().entity(obj.toString()).build();
	}

	@DELETE
	@Path("/remove/{ip}")
	public Response removeChromecast(@PathParam("ip") String ip) {
		JSONObject obj = new JSONObject();
		if (ip != null && !ip.isEmpty()) {
			Vector<CastConnection> vec = SettingsServlet.getConnections();
			for (CastConnection con : vec) {
				if (con.getIp().equals(ip)) {
					vec.remove(con);
					SettingsServlet.setConnections(vec);
					if (SettingsServlet.saveSettings(this.webRequest
							.getServletContext())) {
						obj.put("ok", "");
					} else {
						obj.put("error", "failed to save settings.");
					}
					break;
				}
			}
		} else {
			obj.put("error", "ip and name must be defined.");
		}
		return Response.ok().entity(obj.toString()).build();
	}

}
