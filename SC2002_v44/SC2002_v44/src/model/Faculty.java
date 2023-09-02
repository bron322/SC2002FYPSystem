package model;

import java.util.HashMap;

import enums.EnumMatcher;
import enums.FacultyStatus;
import enums.StudentStatus;
import enums.UserType;

public class Faculty extends User {
	private int numSupervised;
	private FacultyStatus status;

	public Faculty(String name, String supervisorID, String password, Double numSupervised, String Status) 
	{
		super(supervisorID,name, password);
		this.status = EnumMatcher.matchEnum(FacultyStatus.class, Status);
		this.numSupervised = (int)numSupervised.intValue();
	}

	public String getEmail() {
		return getUserID()+ "@ntu.edu.sg";
	}
	
	public UserType getUserType() {
		return UserType.FACULTY;
	}	
	
	public void setStatus(FacultyStatus status) {
		this.status = status;
	}
	
	public FacultyStatus getStatus() {
		return status;
	}
	
	public int getNumSupervised() {
		return numSupervised;
	}
	
	public void incNumSupervised() {
		this.numSupervised = this.numSupervised + 1;
	}
	
	public void decNumSupervised() {
		this.numSupervised = this.numSupervised - 1;
	}

	@Override
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> facultyHashMap = new HashMap<String, Object>();
		facultyHashMap.put("Email", this.getEmail());
		facultyHashMap.put("Name", this.getName());
		facultyHashMap.put("UserID", this.getUserID());
		facultyHashMap.put("Password", this.getPassword());
		facultyHashMap.put("Status", getStatus().name());
		facultyHashMap.put("numSupervised", this.numSupervised);
		
		return facultyHashMap;
	}
	
}
