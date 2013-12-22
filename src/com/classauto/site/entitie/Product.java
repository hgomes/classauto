package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class Product {
	
	private int id;
	
	private static Map<Integer, Product> mapProduct = new HashMap<Integer, Product>();
	
	public static void add(Product product) {
		mapProduct.put(product.getId(), product);
	}
	
	public static Product getById(int id) {
		return mapProduct.get(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
