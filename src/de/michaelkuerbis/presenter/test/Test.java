package de.michaelkuerbis.presenter.test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.michaelkuerbis.presenter.utils.CronJob;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.MediaStatus;

public class Test {

	private final static Logger log = LogManager.getLogger(Test.class);
	
	public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
		ChromeCast cast = new ChromeCast("172.16.2.96");
		log.info("Verbinde");
		cast.connect();
		log.info("Verbunden");
		MediaStatus status = cast.getMediaStatus();
		if(status != null){
			log.info("Medien!!");
		}else{
			log.info("kein Medienstatus zur verfügung");
		}
		cast.disconnect();
	}

}
