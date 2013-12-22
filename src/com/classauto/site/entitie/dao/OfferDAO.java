package com.classauto.site.entitie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.classauto.site.entitie.Category;
import com.classauto.site.entitie.Offer;

public class OfferDAO {
	
	private Connection conn;

	public OfferDAO(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public List<Offer> loadOfferById(List<Integer> offersId) throws SQLException {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		if (offersId.size()>0 && offersId.size()==1) {
			
			SELECT_OFFER = SELECT_OFFER + " AND id = " + offersId.get(0);
			
		} else if (offersId.size()>0 && offersId.size()>1) {
			
			String idsIn = "";
			
			for (Integer id : offersId) {
				
				idsIn = idsIn + id + ",";
				
			}
			
			System.out.println("idsIn=" + idsIn);
			
			idsIn = idsIn.substring(0, idsIn.length()-1);
			
			System.out.println("idsIn=" + idsIn);
			
			SELECT_OFFER = SELECT_OFFER + " AND  id in( " + idsIn + ");";
			
		} else {
			
			return new ArrayList<Offer>();
		}
		
		ResultSet rs = this.conn.createStatement().executeQuery(SELECT_OFFER);
		
		while (rs.next()) {
			
			int i = 0;
			
			Offer offer = new Offer();
			offer.setId(rs.getInt(++i));
			offer.setTitle(rs.getString(++i));
			offer.setDescription(rs.getString(++i));
			
			offers.add(offer);
		}
		
		rs.close();
		
		return offers;
	}
	
	public List<Offer> loadOfferByDate(Calendar startDate, Calendar stopDate) throws SQLException {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		System.out.println("start_date=");
		
		PreparedStatement pstm = null; 
		
		if (stopDate==null)
			pstm = this.conn.prepareStatement(SELECT_OFFER_DATE_MORE);
		else 
			pstm = this.conn.prepareStatement(SELECT_OFFER_DATE);
		
		
		pstm.setTimestamp(1, new java.sql.Timestamp(startDate.getTimeInMillis()));
		
		if (stopDate!=null) 
			pstm.setTimestamp(2, new java.sql.Timestamp(stopDate.getTimeInMillis()));
		
		ResultSet rs = pstm.executeQuery();
		
		while (rs.next()) {
				
			int i = 0;
			
			Offer offer = new Offer();
			offer.setId(rs.getInt(++i));
			offer.setTitle(rs.getString(++i));
			offer.setDescription(rs.getString(++i));
			++i;
			offer.setCategory(Category.getById(rs.getInt(++i)));
			
			offers.add(offer);
		}
			
		rs.close();
			
		return offers;
	}
	
	
	public String SELECT_OFFER_DATE = "SELECT id, titulo, descricao, perfil_id, categoria_id, preco, produto_id, fabricante_id, status " +
									  "FROM oferta " +
									  "WHERE timestamp between ? and ?";
	
	public String SELECT_OFFER_DATE_MORE = "SELECT id, titulo, descricao, perfil_id, categoria_id, preco, produto_id, fabricante_id, status " +
			  							   "FROM oferta " +
			  							   "WHERE timestamp >= ?";
	
	
			
	
	public String SELECT_OFFER = "SELECT id, titulo, descricao, perfil_id, categoria_id, preco, produto_id, fabricante_id, status " +
														   "FROM oferta " +
														   "WHERE status = 1 ";
															

}
