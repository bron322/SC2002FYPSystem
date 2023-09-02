package model;
import java.util.HashMap;

import enums.UserType;

public abstract class User {
	private String userID;
	private String password;
	private String name;
	
	public User(String userID, String name, String password) {
		this.userID = userID;
		this.password = password;
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public abstract String getEmail();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract UserType getUserType();
	
	public abstract HashMap<String, Object> toHashMap();
	
}
