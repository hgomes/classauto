package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class User {
	
	private int id;

	private static Map<Integer, User> mapUser = new HashMap<Integer, User>();
	
	public static void add(User user) {
		
		mapUser.put(user.getId(), user);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
