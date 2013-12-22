package com.classauto.site.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.classauto.site.entitie.Category;
import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.engine.CategoryEngine;

public class CategoryAction extends ActionBase {
	
	private int id;
	
	private List<Offer> offers         = new ArrayList<Offer>();
	private HttpServletRequest request = ServletActionContext.getRequest();

	
	
	@Override
	public String execute() throws Exception {
		System.out.println("DENTRO DA ACTION");
		return SUCCESS;
	}
	
	
	public String showCategory() {
		
		System.out.println("Show Category...");
		
		int id = 0;
		List<Category> categories = null;
		if (request.getParameter("id")==null) {
			
			categories = Category.getAllCategoryFather();
			
		} else {
			
			id 		   = Integer.parseInt(request.getParameter("id"));
			categories = CategoryEngine.getInstance().getCategoryChildren(id);
		}
		
		//Isso significa que ele esta na categoria folha
		//Exibir as ofertas
		if (categories==null || categories.isEmpty()) {
			
			this.request.getSession().setAttribute("categoryId", id);
			
			return SEARCH;
		}
		
		request.setAttribute("categories", categories); 
		
		return SUCCESS;
	}
	
	public String showOffersByCategoryId() {
		
		return SUCCESS;
		
	}
	
	public String teste() {
		
		System.out.println("DENTRO DO METODO TESTE");
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public List<Offer> getOffers() {
		return offers;
	}
	
	
}
