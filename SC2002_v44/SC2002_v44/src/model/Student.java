package model;

import java.util.HashMap;

import Assignment.Database;
import enums.EnumMatcher;
import enums.StudentStatus;
import enums.UserType;

public class Student extends User {
	private StudentStatus status;
	private int projectID; //is -1 if no projectID
	private Project project;
	private boolean newProjectViewed; //initialize default is false
	
	public Student(String name, String studentID, String password, Boolean newProjectViewed, Double projectID, String Status) {
		super(studentID, name, password);
		this.newProjectViewed = newProjectViewed;
		this.projectID = projectID.intValue();
		this.project = Database.PROJECTS.get((int)projectID.intValue());
		this.status = EnumMatcher.matchEnum(StudentStatus.class, Status);
	}

	public String getEmail() {
		return getUserID()+"@e.ntu.edu.sg";
	}
	
	public UserType getUserType() {
		return UserType.STUDENT;
	}
	
	public StudentStatus getStatus() {
		return status;
	}
	
	public void setStatus(StudentStatus status) {
		this.status = status;
	}
	
	public void setProject(Project project) {
		this.project = project;
		if(project == null) {
			this.projectID = -1;
		}
		else {
			this.projectID = project.getProjectID();
		}
	}	
	
	public int getProjectID() {
		return this.projectID;
	}
	
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public boolean isNewProjectViewed() {
		return newProjectViewed;
	}
	
	public void setNewProjectViewed(boolean viewed) {
		this.newProjectViewed = viewed;
	}

	@Override
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> studentHashMap = new HashMap<String, Object>();
		studentHashMap.put("Email", this.getEmail());
		studentHashMap.put("Name", this.getName());
		studentHashMap.put("UserID", this.getUserID());
		studentHashMap.put("Password", this.getPassword());
		studentHashMap.put("Status", getStatus().name());
		studentHashMap.put("ProjectID", this.projectID);
		studentHashMap.put("newProjectViewed", this.newProjectViewed);
		
		return studentHashMap;
	}
	
}
