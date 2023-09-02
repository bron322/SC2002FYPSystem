package model;

import java.util.HashMap;

import Assignment.Database;
import enums.RequestType;

public class FacultyToFYPCoor extends Request {
	private Faculty supervisor;
	private FYPCoordinator fypcoor;
	private String supervisorID;
	private String transferringsupervisorID;

	public FacultyToFYPCoor(int projectID, String supervisorID, String transferringsupervisorID, String Status,
			Boolean isViewed) {
		super(projectID, Status, isViewed);
		this.supervisorID = supervisorID;
		this.transferringsupervisorID = transferringsupervisorID;
		this.supervisor = Database.LOGINFACULTIES.get(supervisorID);
		setRequestType(RequestType.TRANSFERRING);
	}

	public Faculty getCreator() {
		return this.supervisor;
	}

	public String getTransferring() {
		return this.transferringsupervisorID;
	}

	@Override
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> FacultytoFYPCoorHashMap = new HashMap<String, Object>();
		FacultytoFYPCoorHashMap.put("RequestID", getRequestID());
		FacultytoFYPCoorHashMap.put("RequestType", getRequestType().name());
		FacultytoFYPCoorHashMap.put("RequestStatus", getRequestStatus().name());
		FacultytoFYPCoorHashMap.put("ProjectID", getRequestedProjectID());
		FacultytoFYPCoorHashMap.put("SupervisorID", supervisorID);
		FacultytoFYPCoorHashMap.put("isViewed", getisViewed());
		FacultytoFYPCoorHashMap.put("TransferringSupervisorID", transferringsupervisorID);

		return FacultytoFYPCoorHashMap;
	}
}
