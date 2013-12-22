package com.classauto.site.util.search;

import org.apache.solr.common.SolrDocument;

public class SearchUtil {
	
	public static Integer transformDocumentInInteger(SolrDocument document) {
		
		System.out.println(document.getFieldValue(ID));
		
		String id = (String)document.getFieldValue(ID);
		
		return Integer.valueOf(id);
	}
	
	private static final String ID = "id";

}
