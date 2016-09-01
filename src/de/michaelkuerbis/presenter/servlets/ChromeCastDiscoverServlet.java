package de.michaelkuerbis.presenter.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import su.litvak.chromecast.api.v2.ChromeCasts;

public class ChromeCastDiscoverServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986406595023612731L;

	@Override
	public void init() throws ServletException{
		try {
			ChromeCasts.startDiscovery();
			System.out.println("Chromecast Discovery started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
