package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
	
	private int id;
	
	private static Map<Integer, Attribute> mapAttribute = new HashMap<Integer, Attribute>();
	
	public static void add(Attribute attribute) {
		mapAttribute.put(attribute.getId(), attribute);
	}
	
	public static Attribute getById(int id) {
		return mapAttribute.get(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
