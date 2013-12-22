package com.classauto.site.entitie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.classauto.site.entitie.dao.CategoryDAO;
import com.classauto.site.util.Conexao;

public class Category {
	
	private int id;
	private String title;
	private String description;
	private Category categoryFather;
	private List<Category> categoryChildren 		  = new ArrayList<Category>();
	private static Map<Integer, Category> mapCategory = new HashMap<Integer, Category>();
	private static List<Category> categoriesFather    = new ArrayList<Category>();    
	
	private int status;

	public Category(int id, String title, int pid, int status) {
		super();
		this.id = id;
		this.title 	= title;
		this.status = status;
		this.categoryFather = getById(pid);
		
		if (this.categoryFather==null)categoriesFather.add(this);
		else this.categoryFather.addChield(this);
	}

	public static void add(Category category) {
		mapCategory.put(category.getId(), category);
	}
	
	public static Category getById(int id) {
		return mapCategory.get(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static List<Category> getAllCategoryFather() {
		return categoriesFather;
	}
	
	public static List<Category> getAll() {
		
		return new ArrayList<Category>();
	}
	
	public void addChield(Category category) {
		this.categoryChildren.add(category);
	}
	
	public List<Category> getCategoryChildren() {
		return categoryChildren;
	}

	public void setCategoryChildren(List<Category> categoryChildren) {
		this.categoryChildren = categoryChildren;
	}

	public static List<Category> getChildrenById(int id) {
		
		Category category = Category.getById(id);
		
		return category.getCategoryChildren();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategoryFather() {
		return categoryFather;
	}

	public void setCategoryFather(Category categoryFather) {
		this.categoryFather = categoryFather;
	}

	public static Map<Integer, Category> getMapCategory() {
		return mapCategory;
	}

	public static void setMapCategory(Map<Integer, Category> mapCategory) {
		Category.mapCategory = mapCategory;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + this.id;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

	public static void main(String[] args) {
		
		Conexao c = new Conexao();
		
		CategoryDAO categoryDAO = new CategoryDAO(c.getConn());
		
		try {
			categoryDAO.loadCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Category catExemplo = Category.getById(1);
		System.out.println(catExemplo);
		
	}
	
	
	
	public static final int FATHER_CATEGORY = 1;
}
