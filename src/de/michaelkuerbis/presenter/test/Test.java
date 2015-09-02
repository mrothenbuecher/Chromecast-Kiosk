package de.michaelkuerbis.presenter.test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.MediaStatus;

public class Test {

	public static void main(String[] args) throws IOException, GeneralSecurityException, InterruptedException {
		ChromeCast cast = new ChromeCast("172.16.2.96");
		System.out.println("Verbinde");
		cast.connect();
		System.out.println("Verbunden");
		MediaStatus status = cast.getMediaStatus();
		if(status != null){
			System.out.println("Medien!!");
		}else{
			System.out.println("kein Medienstatus zur verfügung");
		}
		cast.disconnect();
	}

}
