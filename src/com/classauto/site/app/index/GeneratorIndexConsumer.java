package com.classauto.site.app.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.engine.SearchEngine;
import com.classauto.site.util.thread.Consumer;

public class GeneratorIndexConsumer extends Consumer<Offer> {

	public GeneratorIndexConsumer(int sleep, List<Offer> objs) {
		super(sleep, objs);
		
		this.sleep = sleep;
	}

	@Override
	public void consume() {
		
		while(true) {
			
			
			if (this.objs!=null && !this.objs.isEmpty()) {
				
				System.out.println("Cosuming of the offer Amount find ... " + objs.size());
				
				List<Offer> tempObjs = new ArrayList<Offer>();
				
				synchronized (this) {
					
					System.out.println(this.objs);
					tempObjs.addAll(this.objs);
					this.objs.clear();
					
				}
				
				List<Document> docs = new ArrayList<Document>();
				
				for (Offer offer : tempObjs) {
					
					docs.add(SearchEngine.getInstance().createDocument(offer));
				}
				
				SearchEngine.getInstance().addIndex(docs);
			}
			
			try {
				
				Thread.currentThread().sleep(this.sleep * this.millisecond);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
