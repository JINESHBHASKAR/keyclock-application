package com.nmsecurity.model;

import java.io.Serializable;

/**
 * 
 * @author Jinesh KP
 *
 */

public class DashBoardDetails implements Serializable{
	
	private static final long serialVersionUID = -8352030524554296225L;
	private String displayName;
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
