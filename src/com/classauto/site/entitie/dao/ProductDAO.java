package com.classauto.site.entitie.dao;

import java.sql.Connection;

public class ProductDAO {
	
	private Connection conn;

	public ProductDAO(Connection conn) {
		super();
		this.conn = conn;
	}

}
