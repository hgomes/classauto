package com.classauto.site.entitie.dao;

import java.sql.Connection;

public class ProfileDAO {
	
	private Connection conn;

	public ProfileDAO(Connection conn) {
		super();
		this.conn = conn;
	}

}
