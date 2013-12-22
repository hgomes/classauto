package com.classauto.site.searchconfig;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class LuceneSearchConfig {
	
	private String index;
	
	private static LuceneSearchConfig instance;
	
	private Directory directory;
	private IndexWriter indexWriter;
	private IndexSearcher search;
	private IndexReader indexReader;
	private Analyzer analyzer;
	
	
	private LuceneSearchConfig() {
		
		//writer
		directory = new RAMDirectory();
		analyzer  = new StandardAnalyzer(Version.LUCENE_40);
	
	}
	
	public static synchronized LuceneSearchConfig getInstance( ) {
		
		if (instance==null)instance = new LuceneSearchConfig();
		
		return instance;
	}
	
	public IndexWriter getIndexWriter() {
		
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		
		try {
			
			this.indexWriter      = new IndexWriter(directory, iwc);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return this.indexWriter;
	}
	
	public IndexSearcher getSearch() throws IOException {
		
		//Reader
		this.indexReader   = IndexReader.open(directory);
	    this.search = new IndexSearcher(this.indexReader);
	    
		return this.search;
				
	}
}
