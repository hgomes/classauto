package com.classauto.site.entitie.engine;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.classauto.site.entitie.Category;
import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.SearchResult;
import com.classauto.site.entitie.dao.OfferDAO;
import com.classauto.site.searchconfig.LuceneSearchConfig;
import com.classauto.site.searchconfig.SolrSearchConfig;
import com.classauto.site.util.Conexao;
import com.classauto.site.util.StringUtil;
import com.classauto.site.util.search.SearchUtil;

public class SearchEngine {
	
	private static SearchEngine searchEngine;
	
	private static CacheManager cacheManager = null;
	private static Map<String, SearchResult> mapSearch = new HashMap<String, SearchResult>();
	
	private SearchEngine() {
		
		Cache cache  = new Cache(new CacheConfiguration("classauto", 10000));	
		cacheManager = CacheManager.getInstance();
		cacheManager.addCache(cache);
	}
	
	public static synchronized SearchEngine getInstance() {
		
		if (searchEngine==null)searchEngine = new SearchEngine();
		
		return searchEngine;
	}
	
	public void add(SearchResult searchResult) {
		mapSearch.put(searchResult.getKeyWord(), searchResult);
	}
	
	public void addCache(SearchResult result) {
		
		System.out.println("Add cache...");
		
		String signature = makeSignature(result.getKeyWord(), result.getCategory());
		Element element  = new Element(signature, result);
		Cache cache      = cacheManager.getCache("classauto");
		cache.put(element);
	}
	
	public SearchResult getCache(String signature) {
		
		Cache cache         = cacheManager.getCache("classauto");
		Element element     = cache.get(signature);
		
		if (element==null)return null;
		
		SearchResult result = (SearchResult)element.getObjectValue();
		
		return result;
	}
	
	public List<Offer> loadOfferByWord(String word) {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		try {
			
			List<Integer> ids = loadInLuceneByWord(word);
			offers = loadOfferByIds(ids);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return offers;
	}
	
	public List<Offer> loadOfferByCategoryId(int id) {
		
		List<Offer> offers = new ArrayList<Offer>();
		
		try {
		
			List<Integer> ids  = loadInLuceneByCategory(id);
			offers             = loadOfferByIds(ids);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return offers;
		
	}
	
	public List<Offer> loadOfferByIds(List<Integer> ids) {
		
		List<Offer> offers = new ArrayList<Offer>();
	
		Connection conn = null;
		
		try {
			
			conn   			  = Conexao.getConn();
			OfferDAO offerDAO = new OfferDAO(conn);
			offers            = offerDAO.loadOfferById(ids);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if (conn!=null && !conn.isClosed()) {
					
					conn.close();
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		return offers;
	}
	
	public SearchResult searchOfferByCategoryId(int categoryId) throws IOException  {
		
		if (categoryId==0)return null;
		
		Category category   = Category.getById(categoryId);
		String signature    = makeSignature(null, category);
		
		SearchResult result = getCache(signature);
		
		if (result!=null) {
			
			System.out.println("Mathing in cache...");
			
			return result;
		}
		
		
		//List<Integer> ids  = loadInLuceneByCategory(categoryId); //by lucene
		List<Integer> ids  = loadInSolrByCategory(categoryId); //by solr
		List<Offer> offers = loadOfferByIds(ids);
		
		result = new SearchResult();
		result.setCategory(Category.getById(categoryId));
		
		addCache(result);
		
		result.setOffers(offers);
		
		return result;
		
	}
	
	public List<Integer> loadInSolrByCategory(int categoryId) {
		
		List<Integer> ids = new ArrayList<Integer>();
		
		SolrServer server = SolrSearchConfig.getInstance().getSolrServer();
		
		SolrQuery query   = new SolrQuery();
		
		query.setQuery(String.valueOf(categoryId));
		
		query.setFields(ID + "," + TITLE + "," + CATEGORYID);
		
		try {
			
			QueryResponse response = server.query(query);
			SolrDocumentList docs  = response.getResults();
			
			if (docs!=null) {
				
				for (int i=0; i<docs.size(); i++) {
					
					ids.add(SearchUtil.transformDocumentInInteger(docs.get(i)));
				}
			}
			
		} catch (SolrServerException e) {
			
			e.getStackTrace();
		}
		
		return ids;
	}
	
	public List<Integer> loadInSolrByWord(String word) {
		
		List<Integer> ids = new ArrayList<Integer>();
		
		SolrServer server = SolrSearchConfig.getInstance().getSolrServer();

		SolrQuery query   = new SolrQuery();
		
		query.setQuery(word);
		query.setFields(ID + "," + TITLE + "," + CATEGORYID);
		
		query.setStart(0);
		
		try {
		
			QueryResponse response  = server.query(query);
			SolrDocumentList docs   = response.getResults();
			
			if (docs!=null) {
				
				for (int i=0; i<docs.size(); i++) {
					
					ids.add(SearchUtil.transformDocumentInInteger(docs.get(i)));
				}
			}
		 
		} catch (SolrServerException e) {
			
			e.printStackTrace();
			
			return new ArrayList<Integer>();
		}
		
		return ids;
	}
	
	public List<Integer> loadInLuceneByCategory(int categoryId) throws IOException {
		
		Term term = new Term("categoryid", String.valueOf(categoryId));
		
		return searchOffer(term);
		
	}
	
	public List<Integer> loadInLuceneByWord(String word) throws IOException {
		
		Term term = new Term("word", StringUtil.normalizeForSearch(word));
		
		return searchOffer(term);
	}
	
	
	public SearchResult searchOfferByWord(String word) throws IOException {
		
		if (word==null)return null;
		
		String signature = makeSignature(word, null);
		
		SearchResult result = getCache(signature);
		
		if (result!=null) {
			
			System.out.println("Matching in cache...");
			
			return result;
		}
		
		//List<Integer> ids  = loadInLuceneByWord(word); //By lucene
		List<Integer> ids  = loadInSolrByWord(word);  //By solr
		List<Offer> offers = loadOfferByIds(ids);
		
		result = new SearchResult();
		result.setKeyWord(word);
		
		addCache(result);
		
		result.setOffers(offers);
		
		return result;
	}
	
	private List<Integer> searchOffer(Term term) throws IOException {
		
		List<Integer> ids 	 = new ArrayList<Integer>();
	
		IndexSearcher search = LuceneSearchConfig.getInstance().getSearch();
		
		Analyzer analyser    =  new StandardAnalyzer(Version.LUCENE_45); 
		
		QueryParser parser   = new QueryParser(Version.LUCENE_45,term.field(), analyser);
		
		Query query = null;
		try {
			
			query = parser.parse(term.text());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TopDocs tops   = search.search(query, 10);
		ScoreDoc[] hit = tops.scoreDocs;
		
		int numTotalHits = tops.totalHits;
		
		System.out.println(numTotalHits + " total matching documents");
		
		for (ScoreDoc scoreDoc : hit) {
			
			Document doc = LuceneSearchConfig.getInstance().getSearch().doc(scoreDoc.doc);
			int id       = Integer.parseInt(doc.get(ID));
			
			ids.add(id);
		}
		
		return ids;
		
	}
	
	public Document createDocument(Offer offer) {
		
		String word = offer.getTitle() + " " + offer.getDescription();
		
		Field idField       = new Field(ID, String.valueOf(offer.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field wordField     = new Field("word", StringUtil.normalizeForSearch(word), Field.Store.YES, Field.Index.ANALYZED);
		Field categoryField = new Field("categoryid", String.valueOf(offer.getCategory().getId()), Field.Store.YES, Field.Index.ANALYZED);
		
		Document document = new Document();
		document.add(idField);
		document.add(wordField);
		document.add(categoryField);
		
		return document;
	}
	
	public Offer createOffer(Document document) {
		return null;
	}
	 
	public void addIndex(List<Document> docs) {
		
		IndexWriter writer = LuceneSearchConfig.getInstance().getIndexWriter();
		
		try {
			
			for (Document doc :docs) {
					
				writer.addDocument(doc);
			}
			
			writer.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public String makeSignature(String keyWord, Category category) {
		return keyWord + " " + ((category!=null) ? String.valueOf(category.getId()) : String.valueOf(0));
	}
	
	private static final String  ID = "id";
	private static final String QUERY_SOLR = "q=";
	private static final String CATEGORYID = "cat";
	private static final String TITLE = "title";
	
	
	
}


