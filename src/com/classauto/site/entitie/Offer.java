package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class Offer {
	
	private int id;
	private String title;
	private String description;
	private double price;
	private Profile profile;
	private Product product;
	private int status;
	private Category category;
	
	
	private static Map<Integer, Offer> mapOffer = new HashMap<Integer, Offer>();
	
	public static void add(Offer offer) {
		mapOffer.put(offer.getId(), offer);
	}
	
	public static Offer getById(int id) {
		return mapOffer.get(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		
		return "ID = ["+ this.id +"]" +" TITLE = ["+this.title+"]";
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
