package com.classauto.site.entitie.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.classauto.site.entitie.Category;

public class CategoryDAO {
	
	private Connection conn;

	public CategoryDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public int loadCategories() throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, titulo, pid, status ");
		sql.append("FROM categoria ");
		sql.append("ORDER BY pid ");
		
		ResultSet rs = this.conn.createStatement().executeQuery(sql.toString());
		
		while (rs.next()) {
			
			Category.add(new Category(rs.getInt("id"), rs.getString("titulo"), rs.getInt("pid"), rs.getInt("status")));
			
		}
		
		return Category.getAll().size();
		
	}
	
	
	
}
