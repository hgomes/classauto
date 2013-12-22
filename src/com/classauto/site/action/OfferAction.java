package com.classauto.site.action;

import java.util.List;

import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.engine.OfferEngine;

public class OfferAction extends ActionBase {
	
	public String showOffers() {
		
		int categoryId = (Integer)request.getSession().getAttribute("id");
		
		List<Offer> offers =  OfferEngine.getInstance().loadOfferByCategoryId(categoryId);
		this.request.setAttribute("offers", offers);
		
		return SUCCESS;
	
	}
	
	

}
