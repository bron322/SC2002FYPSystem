package model;

import java.util.HashMap;

public class FYPCoordinator extends Faculty {
	public FYPCoordinator(String name, String supervisorID, String password, Double numSupervised, String Status) 
	{
		super(name, supervisorID, password, numSupervised, Status);
	}
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> fypCoordHashMap = new HashMap<String, Object>();
		fypCoordHashMap.put("Email", this.getEmail());
		fypCoordHashMap.put("Name", this.getName());
		fypCoordHashMap.put("UserID", this.getUserID());
		fypCoordHashMap.put("Password", this.getPassword());
		fypCoordHashMap.put("Status", getStatus().name());
		fypCoordHashMap.put("numSupervised", getNumSupervised());
		
		return fypCoordHashMap;
	}
	
}
