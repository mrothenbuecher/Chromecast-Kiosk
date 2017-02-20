package de.michaelkuerbis.presenter.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;

@Path("/discovered")
public class DiscoverREST {

	@GET
	@Path("/get")
	public Response start() throws InterruptedException {
		List<ChromeCast> casts = ChromeCasts.get();
		JSONArray array = new JSONArray();
		for (ChromeCast cast : casts) {
			JSONObject obj = new JSONObject();
			obj.put("name", cast.getName());
			obj.put("ip", cast.getAddress());
			array.add(obj);
		}
		return Response.ok(array.toJSONString()).build();

	}

}
