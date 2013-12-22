package com.classauto.site.entitie.engine;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.dao.OfferDAO;
import com.classauto.site.util.Conexao;

public class OfferEngine {
	
	private static OfferEngine offerEngine;
	
	private OfferEngine() {
	}
	
	public static OfferEngine getInstance() {
		
		if (offerEngine==null)offerEngine = new OfferEngine();
		
		return offerEngine;
	}
	
	
	public List<Offer> loadOfferByCategoryId(int categoryId) {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		offers = SearchEngine.getInstance().loadOfferByCategoryId(categoryId);
		
		return offers;
	}
	
	
	public List<Offer> loadOffer(Calendar startDate, Calendar stopDate) {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		Connection conn = Conexao.getConn();
		
		try {
			
			OfferDAO dao = new OfferDAO(conn);
			offers 		 = dao.loadOfferByDate(startDate, (stopDate!=null) ? stopDate : null);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try{conn.close();} catch (Exception e) {e.printStackTrace();}
		}
		
		return offers;
		
	}
	
	public int save(Offer offer) {
		
		Connection conn = null;
		
		return 0;
	}

}
