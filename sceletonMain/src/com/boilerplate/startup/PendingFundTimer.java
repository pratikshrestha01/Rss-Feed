package com.boilerplate.startup;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.IRssItemApi;
import com.rss.parser.EspnMlbParser;
import com.rss.parser.EspnNbaParser;
import com.rss.parser.EspnNflParser;
import com.rss.parser.EspnNhlParser;
import com.rss.parser.EspnRpmParser;

@Component
public class PendingFundTimer {
	
	@Autowired
	private EspnNflParser espnNflParser; 
	@Autowired
	private EspnNbaParser espnNbaParser;
	
	@Autowired
	private EspnMlbParser espnMlbParser;
	
	@Autowired
	private EspnRpmParser espnRpmParser;
	
	@Autowired
	private IRssItemApi rssItemApi;
	
	@Autowired
	private EspnNhlParser  espnNhlParser;

	public void Starter() {
		final PendingFundTimer test = new PendingFundTimer();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				test.doStuff();
			}
		}, 0, (60 * 1000));
	}

	public void doStuff() {
		try {
			System.out.println("hi test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
