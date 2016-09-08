package de.michaelkuerbis.presenter.servlets;

import de.michaelkuerbis.presenter.utils.CastConnection
import de.michaelkuerbis.presenter.utils.CronJob
import groovy.json.JsonSlurper

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.json.simple.JSONArray
import org.json.simple.JSONObject

public class CronServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9121110689011730052L;


	private static def settings;

	private static Vector<CronJob> jobs;

	@Override
	public void init() throws ServletException{
		reloadSettings(this.getServletContext());
		for(CronJob job : jobs){
			job.start();
		}
		System.out.println("cronjobs: "+jobs);
	}

	public static void reloadSettings(ServletContext context){
		jobs = new Vector<CronJob>();
		//TODO no config file ...
		settings = new JsonSlurper().parse(context.getResource("/WEB-INF/config/cron.json"), "UTF-8")
		for(def con in settings.cronjobs){
			CronJob job = new CronJob(con.target, con.url, con.pattern, con.name, con.reload);
			jobs.add(job);
		}
	}

	public static boolean saveSettings(ServletContext context){
		JSONObject obj = new JSONObject();
		JSONArray cronjobs = new JSONArray();
		for(CronJob con : jobs){
			JSONObject foo = new JSONObject();
			foo.put("target", con.getTarget())
			foo.put("name", con.getName());
			foo.put("pattern", con.getPattern());
			foo.put("reload", con.getReload());
			foo.put("url", con.getUrl());
			cronjobs.add(foo);
		}
		obj.put("cronjobs", cronjobs);
		try{
			new File(context.getResource("/WEB-INF/config/cron.json").toURI()).write(obj.toJSONString(), 'UTF-8')
			reloadSettings(context);
			return true;
		}catch(Exception ex){
			ex.printStackTrace()
			return false;
		}
	}

	public static Vector<CronJob> getCronJobs(){
		return jobs;
	}

	public static void setCronJobs(Vector<CronJob> jobs){
		this.jobs = jobs;
	}

	public static JsonSlurper getConfig(){
		return settings;
	}

}
