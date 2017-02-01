package de.michaelkuerbis.presenter.utils;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.TaskExecutor;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.Status;

public class CronJob implements Runnable {

	private String url, target;
	private String pattern, name;

	private Scheduler scheduler;

	private int reload;

	public CronJob(String target, String url, String pattern, String nam,
			int reload) {
		this.url = url;
		this.target = target;
		this.pattern = pattern;
		this.name = nam;
		this.reload = reload;
		this.scheduler = new Scheduler();
		this.scheduler.addSchedulerListener(new SchedulerListener() {

			@Override
			public void taskFailed(TaskExecutor arg0, Throwable arg1) {
				System.out.println("task " + name + " failed reason: "
						+ arg1.getMessage());
			}

			@Override
			public void taskLaunching(TaskExecutor arg0) {
				System.out.println("task " + name + " started");
			}

			@Override
			public void taskSucceeded(TaskExecutor arg0) {
				System.out.println("task " + name + " succeeded");
			}

		});
	}

	public void start(){
		scheduler.schedule(pattern, this);
		scheduler.start();
	}
	
	@Override
	public void run(){
		ChromeCast chromecast = new ChromeCast(target);
		try {
			chromecast.connect();
			if (chromecast.isConnected()) {
				Status status = chromecast.getStatus();
				if (chromecast.isAppAvailable(Settings.appId)) {
					Application app = chromecast.launchApp(Settings.appId);
					chromecast.send("urn:x-cast:de.michaelkuerbis.kiosk",
							new KioskUpdateRequest(url, reload));
					return;
				} else
					throw new Exception("app is not available");
			} else {
				throw new Exception(
						"chromecast did not react / ip of chromecast may wrong");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public String getTarget() {
		return target;
	}

	public String getPattern() {
		return pattern;
	}

	public int getReload() {
		return reload;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setReload(int reload) {
		this.reload = reload;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Scheduler getScheduler(){
		return scheduler;
	}

}
