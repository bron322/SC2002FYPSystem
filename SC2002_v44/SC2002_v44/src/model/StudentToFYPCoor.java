package model;


import java.util.HashMap;


import Assignment.Database;
import enums.EnumMatcher;
import enums.RequestType;

public class StudentToFYPCoor extends Request {
	private String studentID;
	private Student student;
	
	public StudentToFYPCoor(int projectID, String requestType, String studentID, String Status, Boolean isViewed) {
		super(projectID, Status, isViewed);
		this.studentID = studentID;
		student = Database.STUDENTS.get(studentID);
		setRequestType(EnumMatcher.matchEnum(RequestType.class, requestType));
	}
	
	public void deregister(Project project) {
		student.setProject(null);
	}
	
	public Student getCreator() {
		return this.student;
	}
	
	public Project getProject() {
		return Database.PROJECTS.get(super.getRequestedProjectID());
	}
	
	@Override
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> StudentToFYPCoorHashMap = new HashMap<String, Object>();
		StudentToFYPCoorHashMap.put("RequestID", getRequestID());
		StudentToFYPCoorHashMap.put("RequestType", getRequestType().name());
		StudentToFYPCoorHashMap.put("RequestStatus", getRequestStatus().name());
		StudentToFYPCoorHashMap.put("ProjectID", getRequestedProjectID());
		StudentToFYPCoorHashMap.put("StudentID", studentID);
		StudentToFYPCoorHashMap.put("isViewed", getisViewed());
		
		
		return StudentToFYPCoorHashMap;
	}
}
