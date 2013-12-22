package com.classauto.site.entitie;

import java.util.HashMap;
import java.util.Map;

public class Profile {
	
	private static Map<Integer, Profile> mapProfile = new HashMap<Integer, Profile>();
	
	private int id;
	private User user;
	private String adress;
	private long telefone;
	private long telefone2;
	private long ceclphone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public static void add(Profile profile) {
		mapProfile.put(profile.getId(), profile);
	}
	
	public static Profile getId(int id) {
		return mapProfile.get(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public long getTelefone() {
		return telefone;
	}

	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}

	public long getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(long telefone2) {
		this.telefone2 = telefone2;
	}

	public long getCeclphone() {
		return ceclphone;
	}

	public void setCeclphone(long ceclphone) {
		this.ceclphone = ceclphone;
	}
}
