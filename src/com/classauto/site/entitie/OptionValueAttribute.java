package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class OptionValueAttribute {
	
	private int id;
	
	private static Map<Integer, OptionValueAttribute> mapOptionValueAttribute = new HashMap<Integer, OptionValueAttribute>();
	
	public static void add(OptionValueAttribute optionValueAttribute) {
		mapOptionValueAttribute.put(optionValueAttribute.getId(), optionValueAttribute);
	}
	
	public static OptionValueAttribute getById(int id) {
		return mapOptionValueAttribute.get(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
