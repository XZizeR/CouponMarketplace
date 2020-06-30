package com.coupon.web;

import com.coupon.facade.ClientFacade;

public class Session {
	private ClientFacade facade;
	private long lastAccessed; //last time i accessed
	
	public Session(ClientFacade facade, long lastAccessed) {
		super();
		this.facade = facade;
		this.lastAccessed = lastAccessed;
	}

	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public ClientFacade getFacade() {
		return facade;
	}

	public long getLastAccessed() {
		return lastAccessed;
	}
	
}
