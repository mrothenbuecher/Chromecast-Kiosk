package de.michaelkuerbis.presenter.rest;

import java.text.ParseException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.redhogs.cronparser.CronExpressionDescriptor;
import net.redhogs.cronparser.Options;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.michaelkuerbis.presenter.servlets.CronServlet;
import de.michaelkuerbis.presenter.utils.CronJob;

@Path("/cron")
public class CronREST {

	@Context
	private HttpServletRequest webRequest;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/add/{target}")
	public Response addChromecast(@PathParam("target") String target,
			@FormParam("name") String name, @FormParam("url") String url,
			@FormParam("pattern") String pattern, @FormParam("reload") int reload) {
		JSONObject obj = new JSONObject();
		if (target != null && !target.isEmpty() && name != null
				&& !name.isEmpty()) {
			boolean alreadyDefined = false;
			for (CronJob con : CronServlet.getCronJobs()) {
				if (con.getTarget().equals(target) && con.getName().equals(name)) {
					alreadyDefined = true;
					obj.put("error", "cronjob already defined with name: "
							+ con.getName());
					break;
				}
			}
			if (!alreadyDefined) {
				CronJob con = new CronJob(target, url, pattern, name, reload);
				Vector<CronJob> vec = CronServlet.getCronJobs();
				vec.add(con);
				CronServlet.setCronJobs(vec);
				if (CronServlet.saveSettings(this.webRequest
						.getServletContext())) {
					con.start();
					obj.put("ok", "");
				} else {
					obj.put("error", "failed to save cronjob.");
				}
			}
		} else {
			obj.put("error", "ip and name must be defined.");
		}
		return Response.ok().entity(obj.toString()).build();
	}

	@GET
	@Path("/get")
	public Response getCronJobs() {
		JSONArray status = new JSONArray();
		for (CronJob job : CronServlet.getCronJobs()) {
			JSONObject foo = new JSONObject();
			foo.put("target", job.getTarget());
			foo.put("name", job.getName());
			foo.put("pattern", job.getPattern());
			foo.put("reload", job.getReload());
			foo.put("url", job.getUrl());
			foo.put("isrunning", job.getScheduler().isStarted());
			try {
				//TODO Language 
				foo.put("desc", CronExpressionDescriptor.getDescription(job.getPattern(), Options.twentyFourHour()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			status.add(foo);
		}
		return Response.ok(status.toJSONString()).build();
	}

	@DELETE
	@Path("/remove/{target}/{name}")
	public Response removeChromecast(@PathParam("target") String target, @PathParam("name") String name) {
		JSONObject obj = new JSONObject();
		if (target != null && !target.isEmpty() && name != null && !name.isEmpty()) {
			Vector<CronJob> vec = CronServlet.getCronJobs();
			for (CronJob con : vec) {
				if (con.getTarget().equals(target) && con.getName().equals(name)) {
					con.stop();
					vec.remove(con);
					CronServlet.setCronJobs(vec);
					if (CronServlet.saveSettings(this.webRequest
							.getServletContext())) {
						if(con.getScheduler().isStarted())
							con.getScheduler().stop();
						obj.put("ok", "");
					} else {
						obj.put("error", "failed to save cronjobs.");
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
