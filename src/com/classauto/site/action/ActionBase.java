package com.classauto.site.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ActionBase extends ActionSupport {
	
	//Nome dos foward
	public static final String VIEW   	   = "view";
	public static final String 	SHOW_OFFER = "show_offers";
	public static final String SEARCH 	   = "search";
										      
	protected HttpServletRequest request = ServletActionContext.getRequest();
}
