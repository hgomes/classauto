package com.classauto.site.action;

import com.classauto.site.app.index.GeneratorIndex;


public class GeneratorIndexAction extends ActionBase {
	
	
	public String generator() {
		
		GeneratorIndex generatorIndex = new GeneratorIndex();
		generatorIndex.generatorIndice();
		
		return SUCCESS;
	}

}
