package com.classauto.site.entitie;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	
	private String keyWord;
	private List<Offer> offers = new ArrayList<Offer>();
	private Category category;
	
	public SearchResult() {
		
	}
	
	public SearchResult(String keyWord, List<Offer> offers) {
		super();
		this.keyWord = keyWord;
		this.offers = offers;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
