package com.classauto.site.entitie.engine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.classauto.site.entitie.Category;
import com.classauto.site.entitie.dao.CategoryDAO;
import com.classauto.site.util.Conexao;

public class CategoryEngine {
	
	private static CategoryEngine engine;
	
	private CategoryEngine() {
		
	}
	
	public static CategoryEngine getInstance() {
		
		if (engine==null)engine = new CategoryEngine();
		
		return engine;
	}
	
	public void loadCategory() {
		
		System.out.println("Load Categories...");
			
		Connection conn = Conexao.getConn();
		
		CategoryDAO dao = new CategoryDAO(conn);
		
		try {
			
			dao.loadCategories();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	public List<Category> getCategoryChildren(int idPhater) {
		
		if (idPhater==0)return new ArrayList<Category>();
		
		return Category.getChildrenById(idPhater);
	}

}
