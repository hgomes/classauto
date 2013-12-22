package com.classauto.site.app.index;

import java.util.Calendar;
import java.util.List;

import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.engine.OfferEngine;
import com.classauto.site.util.thread.Producer;


public class GeneratorIndexProducer extends Producer<Offer> {
	
	public GeneratorIndexProducer(int sleep, List<Offer> objs) {
		
		super(sleep, objs);
		
		this.sleep = sleep;
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void produce() {
		
		while(true) {
			
			loadOffer();
			
			try {
				
				Thread.currentThread().sleep(this.sleep * this.millisecond);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void loadOffer() {
		
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.MINUTE, -5);
		
		
		System.out.println("hora=" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		System.out.println("minuto=" + Calendar.getInstance().get(Calendar.MINUTE));
		
		Calendar stopDate  = Calendar.getInstance();
		
		List<Offer> offers = OfferEngine.getInstance().loadOffer(startDate, null);
		
		System.out.println("Producing of the offer Amount find ... " + offers.size());
		
		for (Offer offer : offers) {
			add(offer);
		}
	}
}
