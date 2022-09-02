package com.nmsecurity.model;

import java.io.Serializable;

/**
 * 
 * @author Jinesh KP
 *
 */

public class StatusVo implements Serializable{
	
	private static final long serialVersionUID = 861717560970753547L;
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
