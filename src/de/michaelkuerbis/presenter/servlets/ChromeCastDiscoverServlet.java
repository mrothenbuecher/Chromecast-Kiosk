package de.michaelkuerbis.presenter.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ChromeCastDiscoverServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986406595023612731L;

	@Override
	public void init() throws ServletException{
		/*
		try {
			
			ChromeCasts.startDiscovery();
			ChromeCasts.registerListener(new ChromeCastsListener(){

				@Override
				public void chromeCastRemoved(ChromeCast arg0) {
					System.out.println("Cast removed: "+arg0.getName()+" "+arg0.getAddress());
					
				}

				@Override
				public void newChromeCastDiscovered(ChromeCast arg0) {
					System.out.println("Cast found: "+arg0.getName()+" "+arg0.getAddress());
				}
				
			});
			
			System.out.println("Chromecast Discovery started");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
}
