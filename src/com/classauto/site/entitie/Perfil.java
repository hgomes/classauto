package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class Perfil {
	
	private int id;
	
	private static Map<Integer, Perfil> mapPerfil = new HashMap<Integer, Perfil>();
	
	public static void add(Perfil perfil) {
		mapPerfil.put(perfil.getId(), perfil);
	}
	
	public static Perfil getById(int id) {
		return mapPerfil.get(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
