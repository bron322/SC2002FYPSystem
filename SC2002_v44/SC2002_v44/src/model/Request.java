package model;

import java.util.HashMap;

import enums.EnumMatcher;
import enums.RequestStatus;
import enums.RequestType;
import enums.StudentStatus;

public abstract class Request {
	private int requestID;
	private static int lastrequestID=1;
	private RequestStatus status;
	private RequestType type;
	private User user;
	private int projectID;
	private boolean isViewed;
	
	public Request(int projectID, String Status, Boolean isViewed){
		this.requestID = lastrequestID++;
        status = RequestStatus.PENDING;
        this.projectID = projectID;
        this.isViewed = isViewed;
        this.status = EnumMatcher.matchEnum(RequestStatus.class, Status);
	}
	
	public void setViewed(boolean viewed) {
		this.isViewed = viewed;
	}
	
	public boolean getisViewed(){
		return this.isViewed;
	}
	
	public int getRequestedProjectID() {
		return this.projectID;
	}
//	
	public RequestStatus getRequestStatus() {
		return this.status;
	}
	
	public RequestStatus setRequestStatus(RequestStatus newStatus) {
		this.status = newStatus;
		return this.status;
	}
	
	public RequestType getRequestType() {
		return this.type;
	}
	public void setRequestType(RequestType requestType) {
		this.type = requestType;
	}

	public int getRequestID() {
		return requestID;
	}
	
	public abstract User getCreator();
	
	public abstract HashMap<String, Object> toHashMap();
	
}
