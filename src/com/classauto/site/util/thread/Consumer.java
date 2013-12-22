package com.classauto.site.util.thread;

import java.util.ArrayList;
import java.util.List;

public abstract class Consumer<T> extends Thread {
	
	//in minute
	protected int sleep = 1;
	
	protected int millisecond = 1000;
	
	protected List<T> objs;
	
	public Consumer(int sleep, List<T> objs) {
		
		this.objs  = objs; 
	
	}
	
	public void add(T t) {
		this.objs.add(t);
	}
	
	@Override
	public void run() {
		
		consume();
	}
	
	public abstract void consume();
	
}
