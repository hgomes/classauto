package com.classauto.site.util.thread;

import java.util.ArrayList;
import java.util.List;

public abstract class Producer<T> extends Thread {
	
	//in minute
	protected int sleep = 1;
	
	protected int millisecond = 1000;
	
	protected List<T> objs;
	
	public Producer(int sleep, List<T> objs) {
		
		this.objs = objs;
	}
	
	@Override
	public void run() {
		
		produce();
	}
	
	public void add(T t) {
		
		this.objs.add(t);
	}

	public abstract void produce();
	

}
