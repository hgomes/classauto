package com.classauto.site.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.SearchResult;
import com.classauto.site.entitie.engine.SearchEngine;

public class SearchAction extends ActionBase {
	
	public String search() {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		String word       = request.getParameter("p");
		String categoryId = request.getParameter("id");
		
		//Cleaned the the param of the session
		if (this.request.getSession().getAttribute("categoryId")!=null) {
			
			int id     =  (Integer)this.request.getSession().getAttribute("categoryId");
			categoryId = String.valueOf(id);
			this.request.getSession().setAttribute("categoryId", null);
			
		}
		
		SearchResult result = new SearchResult();
		
		if (word!=null && categoryId==null) {
			
			try {
				
				result = SearchEngine.getInstance().searchOfferByWord(word);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				result = new SearchResult();
			}
			
		} else if (word==null && categoryId!=null) {
			
			try {
				
				result = SearchEngine.getInstance().searchOfferByCategoryId(Integer.parseInt(categoryId));
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				result = new SearchResult();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				result = new SearchResult();
			}
		}
		 
		offers = result.getOffers();
		
		this.request.setAttribute("offers", offers);
		
		return SUCCESS;
	}
 
}
