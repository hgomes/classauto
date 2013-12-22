package com.classauto.site.searchconfig;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

public class SolrSearchConfig {
	
	private HttpSolrServer solrServer;
	
	private static SolrSearchConfig solrSearchConfig;
	
	public static synchronized SolrSearchConfig getInstance() {
		
		if (solrSearchConfig==null)solrSearchConfig = new SolrSearchConfig();
		
		return solrSearchConfig;
	}
	
	private SolrSearchConfig() {
		
		this.solrServer = new HttpSolrServer("http://localhost:8983/solr");
		this.solrServer.setParser(new XMLResponseParser());
		                                      
	}
	
	public SolrServer getSolrServer() {
		return this.solrServer;
	}
}
