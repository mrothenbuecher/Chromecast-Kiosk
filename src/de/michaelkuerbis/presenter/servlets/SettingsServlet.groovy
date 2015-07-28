package de.michaelkuerbis.presenter.servlets;

import de.michaelkuerbis.presenter.utils.CastConnection
import groovy.json.JsonSlurper

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.json.simple.JSONArray
import org.json.simple.JSONObject

public class SettingsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9121110689011730052L;

	
	private static def settings;
	
	private static Vector<CastConnection> connection;
	
	@Override
	public void init() throws ServletException{
		reloadSettings(this.getServletContext());
	}
	
	public static void reloadSettings(ServletContext context){
		connection = new Vector<CastConnection>();
		//TODO no config file ...
		settings = new JsonSlurper().parse(context.getResource("/WEB-INF/config/settings.json"), "UTF-8")
		for(def con in settings.castconnections){
			connection.add(new CastConnection(con.ip, con.name));
		}
	}
	
	public static boolean saveSettings(ServletContext context){
		JSONObject obj = new JSONObject();
		JSONArray castconnections = new JSONArray();
		for(CastConnection con : connection){
			JSONObject foo = new JSONObject();
			foo.put("ip", con.getIp())
			foo.put("name", con.getName());
			castconnections.add(foo);
		}
		obj.put("castconnections", castconnections);
		try{
			new File(context.getResource("/WEB-INF/config/settings.json").toURI()).write(obj.toJSONString(), 'UTF-8')
			reloadSettings(context);
			return true;
		}catch(Exception ex){
			ex.printStackTrace()
			return false;
		}
	}
	
	public static Vector<CastConnection> getConnections(){
		return connection;
	}
	
	public static void setConnections(Vector<CastConnection> connection){
		this.connection = connection;
	}
	
	public static JsonSlurper getConfig(){
		return settings;
	}
	
}
