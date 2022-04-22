package de.michaelkuerbis.presenter.servlets;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.michaelkuerbis.presenter.utils.CronJob;

public class CronServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9121110689011730052L;

	private final static Logger log = LogManager.getLogger(CronServlet.class);

	private static JSONObject settings;
	private static Vector<CronJob> jobs;

	@Override
	public void init() throws ServletException {
		reloadSettings(this.getServletContext());
		for (CronJob job : jobs) {
			job.start();
		}
		log.trace(jobs);
	}

	public static void reloadSettings(ServletContext context) {
		jobs = new Vector<CronJob>();
		// TODO no config file ...
		JSONParser parser = new JSONParser();

		// parser.par
		try {
			settings = (JSONObject) parser
					.parse(new InputStreamReader(context.getResourceAsStream("/WEB-INF/config/cron.json")));
			for (Object obj : ((JSONArray) settings.get("cronjobs")).toArray()) {
				JSONObject con = (JSONObject) obj;
				CronJob job = new CronJob((String) con.get("target"), (String) con.get("url"),
						(String) con.get("pattern"), (String) con.get("name"), (Long) con.get("reload"));
				jobs.add(job);
			}
		} catch (IOException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		}

	}

	public static boolean saveSettings(ServletContext context) {
		JSONObject obj = new JSONObject();
		JSONArray cronjobs = new JSONArray();
		for (CronJob con : jobs) {
			JSONObject foo = new JSONObject();
			foo.put("target", con.getTarget());
			foo.put("name", con.getName());
			foo.put("pattern", con.getPattern());
			foo.put("reload", con.getReload());
			foo.put("url", con.getUrl());
			cronjobs.add(foo);
		}
		obj.put("cronjobs", cronjobs);

		// new FileWriter()
		try (FileWriter file = new FileWriter(context.getResource("/WEB-INF/config/cron.json").getFile())) {
			file.write(obj.toJSONString());
			file.flush();
			reloadSettings(context);
			return true;
		} catch (IOException e) {
			log.error(e);
			return false;
		}
	}

	public static Vector<CronJob> getCronJobs() {
		return jobs;
	}

	public static void setCronJobs(Vector<CronJob> job) {
		jobs = job;
	}

	public static JSONObject getConfig() {
		return settings;
	}

}