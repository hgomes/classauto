package com.classauto.site.app.index;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.classauto.site.util.Conexao;

public class GeneratorIndexForSolr {
	
	private HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr");
	
	private static final String SELECT_FULL_OFFER = "select id, titulo, descricao, categoria_id from oferta";
	
	public void loadOfferIndDB() throws SQLException {
	
		Connection conn = null;
		
		try {
			
			int numFound = 0;
			
			conn 	     = Conexao.getConn();
			ResultSet rs = conn.createStatement().executeQuery(SELECT_FULL_OFFER);
			
			while (rs.next()) {
				
				SolrInputDocument doc = new SolrInputDocument();
				
				doc.addField("id", String.valueOf(rs.getInt("id")));
			    doc.addField("cat", String.valueOf(rs.getInt("categoria_id")));
			    doc.addField("title", rs.getString("titulo") + " " +rs.getString("descricao"));
			    
			    server.add(doc);
			    
			    ++numFound;
			}
			
			System.out.println("Amount found..." + numFound);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (SolrServerException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			if (conn!=null) {
				conn.close();
			}
		}
		
	}
	
	public static void main(String[] args) {
		GeneratorIndexForSolr generator = new GeneratorIndexForSolr();
		try {
			generator.loadOfferIndDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
