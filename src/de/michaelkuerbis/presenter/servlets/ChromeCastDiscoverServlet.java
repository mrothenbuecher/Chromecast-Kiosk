package de.michaelkuerbis.presenter.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.michaelkuerbis.presenter.utils.CronJob;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;
import su.litvak.chromecast.api.v2.ChromeCastsListener;

public class ChromeCastDiscoverServlet extends HttpServlet {

	/**
	 * 
	 */
	
	private final static Logger log = LogManager.getLogger(ChromeCastDiscoverServlet.class);
	
	private static final long serialVersionUID = -6986406595023612731L;

	@Override
	public void init() throws ServletException{
		
		try {
			
			ChromeCasts.startDiscovery();
			ChromeCasts.registerListener(new ChromeCastsListener(){

				@Override
				public void chromeCastRemoved(ChromeCast arg0) {
					log.info("Cast removed: "+arg0.getName()+" "+arg0.getAddress());
					
				}

				@Override
				public void newChromeCastDiscovered(ChromeCast arg0) {
					log.info("Cast found: "+arg0.getName()+" "+arg0.getAddress());
				}
				
			});
			
			log.info("Chromecast Discovery started");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e);
		}
		
	}
	
}
