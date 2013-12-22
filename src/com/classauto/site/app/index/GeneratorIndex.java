package com.classauto.site.app.index;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.lucene.document.Document;

import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.dao.OfferDAO;
import com.classauto.site.entitie.engine.CategoryEngine;
import com.classauto.site.entitie.engine.SearchEngine;
import com.classauto.site.util.Conexao;

public class GeneratorIndex {
	
	private static Calendar startDate;
	private static Calendar stopDate;
	
	public GeneratorIndex() {
		
		startDate = Calendar.getInstance();
		startDate.add(Calendar.MONTH, -6);
		
		stopDate = Calendar.getInstance();
		stopDate.add(Calendar.MONTH, +6);
		
		loadEntities();
		
	}
	
	public void loadEntities() {
		
		CategoryEngine.getInstance().loadCategory();
	}
	
	
	public void generatorIndice(List<Offer> offers) {
		
		List<Document> documents = new ArrayList<Document>();
		
		for (Offer offer : offers) {
			
			documents.add(SearchEngine.getInstance().createDocument(offer));
		}
		
		System.out.println("Amunt offers for to index " + offers.size());
		
		SearchEngine.getInstance().addIndex(documents);
		
	}
	
	public void generatorIndice() {
		
		Connection conn = Conexao.getConn();
		
		try {
			
			OfferDAO offerDao  = new OfferDAO(conn);
			List<Offer> offers = offerDao.loadOfferByDate(startDate, stopDate);
			generatorIndice(offers);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			if (conn!=null) {
				
				try { conn.close(); } catch (Exception e) { }
			}
		}
		
	}
	
	public void criandoIndice(List<Offer> offers) {
		
		List<Document> docs = new ArrayList<Document>();
		
		for (Offer offer : offers) {
			
			Document doc = SearchEngine.getInstance().createDocument(offer);
			docs.add(doc);
		}
		
		SearchEngine.getInstance().addIndex(docs);
	}
	
	public static void main(String[] args) {
		
		GeneratorIndex generatorIndex = new GeneratorIndex();
		generatorIndex.generatorIndice();
		
	}
	
	

}
