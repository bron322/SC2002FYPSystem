package model;

import java.util.HashMap;

import Assignment.Database;
import enums.EnumMatcher;
import enums.ProjectStatus;
import enums.StudentStatus;

public class Project {
	private static int lastprojectID=1;
	private int projectID;
	private String projectTitle;
	private ProjectStatus status;
	private String supervisorID;
	private String studentID;
	private Faculty supervisor;
	private Student student;
	private Boolean isViewedbyFaculty;
	
	public Project(String projectTitle, String supervisorID, String studentID, String Status, Boolean isViewedbyFaculty) {
		this.projectTitle = projectTitle;
        this.projectID = lastprojectID++;
        this.supervisor = Database.LOGINFACULTIES.get(supervisorID);
        //check if excel stores no userID
        this.supervisorID = supervisorID;
        this.studentID = studentID;
        if (!studentID.equals("NULL"))
        	{
        		this.student = Database.STUDENTS.get(studentID);
        	}
        this.status = EnumMatcher.matchEnum(ProjectStatus.class, Status);
        this.isViewedbyFaculty = isViewedbyFaculty;
	}
	
	public void setViewedbyFaculty(boolean viewed) {
		this.isViewedbyFaculty = viewed;
	}
	
	public boolean getViewedbyFaculty() {
		return this.isViewedbyFaculty;
	}
	
	public ProjectStatus getProjectStatus() {
		return this.status;
	}
	
	public Faculty getSupervisor() {
		return supervisor;
	}	
	
	public void setSupervisor(Faculty newSupervisor) {
		this.supervisor = newSupervisor;
		this.supervisorID = newSupervisor.getUserID();
	}
	
	public Student getStudent() {
		return this.student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
		this.studentID = student.getUserID();
	}
	
	public void deregisterStudent() {
		this.studentID = "NULL";
		this.student = null;
	}
	
	public int getProjectID() {
		return projectID;
	}	
	
	public String getProjectTitle() {
		return projectTitle;
	}
	
	public void setProjectTitle(String newProjectTitle) {
		this.projectTitle = newProjectTitle;
	}
	
	public void setProjectStatus(ProjectStatus newStatus) {
		this.status = newStatus;
	}
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> projectHashMap = new HashMap<String, Object>();
		projectHashMap.put("ProjectID", projectID);
		projectHashMap.put("Title", projectTitle);
		projectHashMap.put("Supervisor", supervisor.getName());
		projectHashMap.put("supervisorID", supervisorID);
		projectHashMap.put("studentID", studentID);
		projectHashMap.put("Status", getProjectStatus().name());
		projectHashMap.put("isViewedbyFaculty", getViewedbyFaculty());
		
		return projectHashMap;
	}
}
