package com.classauto.site.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.classauto.site.app.index.GeneratorIndexConsumer;
import com.classauto.site.app.index.GeneratorIndexProducer;
import com.classauto.site.entitie.Offer;
import com.classauto.site.entitie.engine.CategoryEngine;

public class ClassAutoFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		System.out.println("Filter...");
		
		filterChain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		System.out.println("Filter Init");
		
		
		System.out.println("Entities Init");
		CategoryEngine.getInstance().loadCategory();
		
		
		System.out.println("Starting engine producer and consumer");
		
		List<Offer> offerFila = new ArrayList<Offer>();
		
		GeneratorIndexProducer producer = new GeneratorIndexProducer(15, offerFila);
		GeneratorIndexConsumer consumer = new GeneratorIndexConsumer(15, offerFila);
		
		producer.start();
		consumer.start();
		
		//GeneratorIndex generatorIndex = new GeneratorIndex();
		//generatorIndex.generatorIndice();
		
	}

}
