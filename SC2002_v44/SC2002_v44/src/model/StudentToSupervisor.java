package model;

import java.util.HashMap;

import Assignment.Database;
import enums.RequestType;

public class StudentToSupervisor extends Request {
	private Student student;
	private Project project;
	private String newTitle;
	private String origTitle;
	private String studentID;
	
	public StudentToSupervisor(int projectID, String newTitle, String studentID, String Status, String origTitle, Boolean isViewed) {
		super(projectID, Status, isViewed);
		this.newTitle = newTitle;
		student = Database.STUDENTS.get(studentID);
		this.project = Database.PROJECTS.get(projectID);
		this.origTitle = origTitle;
		this.studentID = studentID;
		setRequestType(RequestType.TITLECHANGING);
	}
	
	public void changeTitle(Project project, String newTitle) 
	{
		project.setProjectTitle(newTitle);
	}
	
	public Student getCreator()
	{
		return student;
	}

	public Project getProject() {
		return project;
	}
	
	public void setOrigTitle(String origTitle) {
		this.origTitle = origTitle;
	}
	
	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

	public String getNewTitle() {
		return this.newTitle;
	}
	
	public String getOrigTitle() {
		return this.origTitle;
	}
	
	@Override
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> StudentToSupervisorHashMap = new HashMap<String, Object>();
		StudentToSupervisorHashMap.put("RequestID", getRequestID());
		StudentToSupervisorHashMap.put("RequestType", getRequestType().name());
		StudentToSupervisorHashMap.put("RequestStatus", getRequestStatus().name());
		StudentToSupervisorHashMap.put("ProjectID", getRequestedProjectID());
		StudentToSupervisorHashMap.put("StudentID", studentID);
		StudentToSupervisorHashMap.put("isViewed", getisViewed());
		StudentToSupervisorHashMap.put("originalTitle", getOrigTitle());
		StudentToSupervisorHashMap.put("newTitle", getNewTitle());
		
		
		
		return StudentToSupervisorHashMap;
	}
	
}
