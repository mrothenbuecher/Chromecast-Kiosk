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

import de.michaelkuerbis.presenter.utils.CastConnection;

public class SettingsServlet extends HttpServlet {

	private final static Logger log = LogManager.getLogger(SettingsServlet.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9121110689011730052L;

	private static JSONObject settings;

	private static Vector<CastConnection> connection;

	@Override
	public void init() throws ServletException {
		reloadSettings(this.getServletContext());
	}

	public static void reloadSettings(ServletContext context) {
		connection = new Vector<CastConnection>();
		// TODO no config file ...
		JSONParser parser = new JSONParser();

		try {
			settings = (JSONObject) parser
					.parse(new InputStreamReader(context.getResourceAsStream("/WEB-INF/config/settings.json")));
			for (Object obj : ((JSONArray) settings.get("castconnections")).toArray()) {
				JSONObject con = (JSONObject) obj;
				if (con.get("isDefault") == null) {
					connection.add(new CastConnection((String) con.get("ip"), (String) con.get("name")));
				} else {
					connection.add(new CastConnection((String) con.get("ip"), (String) con.get("name"),
							(boolean) con.get("isDefault")));
				}
			}
		} catch (IOException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		}
		
	}

	public static boolean saveSettings(ServletContext context) {
		JSONObject obj = new JSONObject();
		JSONArray castconnections = new JSONArray();
		for (CastConnection con : connection) {
			JSONObject foo = new JSONObject();
			foo.put("ip", con.getIp());
			foo.put("name", con.getName());
			foo.put("isDefault", con.isDefault());
			castconnections.add(foo);
		}
		obj.put("castconnections", castconnections);
		try (FileWriter file = new FileWriter(context.getResource("/WEB-INF/config/settings.json").getFile())) {
			file.write(obj.toJSONString());
			file.flush();
			reloadSettings(context);
			return true;
		} catch (IOException e) {
			log.error(e);
			return false;
		}
	}

	public static Vector<CastConnection> getConnections() {
		return connection;
	}

	public static void setConnections(Vector<CastConnection> con) {
		connection = con;
	}

	public static JSONObject getConfig() {
		return settings;
	}

}
