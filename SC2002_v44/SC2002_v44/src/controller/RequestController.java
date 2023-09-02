package controller;
import java.util.ArrayList;

import Assignment.Database;
import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import enums.StudentStatus;
import model.FacultyToFYPCoor;
import model.Project;
import model.Request;
import model.Student;
import model.StudentToFYPCoor;
import model.StudentToSupervisor;
import model.User;
import view.FacultyView;
import view.FYPCoordinatorView;
import view.StudentView;

/** 
 * A controller class that acts as a "middle man" between the
 * view classes {@link FacultyView}, {@link FYPCoordinatorView}, {@link StudentView} 
 * and the model classes {@link Request}, {@link StudentToSupervisor}, {@link StudentToFYPCoor},
 * {@link FacultyToFYPCoor}. <p>
 * 
 * It can create, update or filter {@link Request} details.
 * @author Chung Zhi Xuan
 * @version 1.0
 * @since 2023-04-09
 */

public class RequestController{
	/**
     * Creates a new RequestController.
     */
    public RequestController() {
    	
    }
    /**
     * Gets the history of all requests.
     * @return The list of requests made by all Users.
     */
    //To view all HISTORY request
    public static ArrayList<Request> getHistoryRequests() {
        ArrayList<Request> historyRequests = new ArrayList<>();
        for(Request request: Database.REQUESTS.values()) {
        	historyRequests.add(request);
        }
        return historyRequests;
    }
    /**
     * Gets the requests made by a specific User.
     * @param userID The user ID of the user.
     * @return The list of requests made by the user.
     */
    //for user to get specific requests made by user in order
    public static ArrayList<Request> getMyRequests(String userID) {
    	User user = Database.USERS.get(userID);
        ArrayList<Request> myRequests = new ArrayList<>();
        for(Request request: Database.REQUESTS.values()) {
        	if(request.getCreator().equals(user)){
        		myRequests.add(request);
        	}
        }
        return myRequests;
    }
    /**
     * Gets the requests made by all Students to FYP Coordinator.
     * @return The list of pending requests from students to FYP coordinator.
     */
    //To view all requests from student to FYP coord(this is to select projects only)
    public static ArrayList<StudentToFYPCoor> getStudentRequest() {
        ArrayList<StudentToFYPCoor> studentRequests = new ArrayList<>();
        for(StudentToFYPCoor request: Database.StudentREQUESTS.values()) {
        		studentRequests.add(request);
        }
        return studentRequests;
    }
    /**
     * Gets the requests made by all Supervisors to FYP Coordinator.
     * @return The list of pending requests from supervisors to FYP coordinator.
     */
    //To view all requests from supervisor to FYPCoor
    public static ArrayList<FacultyToFYPCoor> getFacultyRequests() {
    	ArrayList<FacultyToFYPCoor> facultyRequests = new ArrayList<>();
    	for(FacultyToFYPCoor request: Database.FacultyREQUESTS.values()) {
    		facultyRequests.add(request);
    	}
    	return facultyRequests;
    }
    /**
     * Gets the requests made by all Students to a specific Supervisor.
     * @return The list of pending requests from students to supervisor.
     */
    //To view all requests from student to Supervisor
    public static ArrayList<StudentToSupervisor> getSupRequests() {
        ArrayList<StudentToSupervisor> supRequests = new ArrayList<>();
        for(StudentToSupervisor request: Database.stu_to_sup_REQUESTS.values()) {
        	supRequests.add(request);
        }
        return supRequests;
    }
    /**
     * Adds a request from Student to FYP Coordinator into REQUESTS and StudentREQUESTS maps.
     * @param request The request made by the student.
     * @param projectID The project ID of the project requested.
     * @return {@code true} if the request was successful. Otherwise, {@code false} if the request 
     * has already been made.
     */
    //for student to FYP requests
    public static boolean addStudentToFYPCoorRequest(StudentToFYPCoor request,int projectID) {
        if(Database.REQUESTS.containsKey(request.getRequestID())) {
            return false;
        }
        Database.REQUESTS.put(request.getRequestID(), request);
        Database.StudentREQUESTS.put(request.getRequestID(), request);
        
        if (request.getRequestType() == RequestType.SELECTING) {
     
        	if(Database.PROJECTS.containsKey(projectID)) {
        		Project project = Database.PROJECTS.get(projectID);
        		project.setProjectStatus(ProjectStatus.RESERVED);
        	}
        }
        return true;
    }
    /**
     * Adds a request from Student to Supervisor into REQUESTS and stu_to_sup_REQUESTS maps.
     * @param request The request made by the student.
     * @return {@code true} if the request was successful. Otherwise, {@code false} if 
     * the request has already been made.
     */
    //for student to supervisor requests
    public static boolean addStudentToSupRequest(StudentToSupervisor request) {
        if(Database.REQUESTS.containsKey(request.getRequestID())) {
            return false;
        }
        Database.REQUESTS.put(request.getRequestID(), request);
        Database.stu_to_sup_REQUESTS.put(request.getRequestID(), request);
        return true;
    }
    /**
     * Gets a Request with a specific request ID.
     * @param requestId The request ID of the request.
     * @return The request with the given request ID.
     */
    public static Request getRequestById(int requestId) {
        return Database.REQUESTS.get(requestId);
    }
    /**
     * Gets all of the pending requests made by all Users.
     * @return The list of all pending requests.
     */
    //To view all PENDING requests
    public static ArrayList<Request> getAllRequests() {
        ArrayList<Request> allRequests = new ArrayList<>();
        for(Request request: Database.REQUESTS.values()) {
        	if(request.getRequestStatus()==RequestStatus.PENDING) {
        		allRequests.add(request);
        	}
        }
        return allRequests;
    }
    /**
     * Adds a request from Supervisor to FYP Coordinator into REQUESTS and FacultyREQUESTS maps.
     * @param request The request made by the supervisor.
     */
    public static void addSupervisorRequest(FacultyToFYPCoor request) {
        Database.REQUESTS.put(request.getRequestID(), request);
        Database.FacultyREQUESTS.put(request.getRequestID(), request);
    }
    /**
     * Checks if there are any pending requests from Student to FYP Coordinator.
     * @return {@code true} if there are any pending requests. 
     * Otherwise, {@code false} if there are no pending requests.
     */
    //to see if there is any request for FYPCoor from Student
    public static boolean anyPendingStudentToFYPCoor() {
    	for(Request request : Database.StudentREQUESTS.values()) {
    		if(request.getRequestStatus() == RequestStatus.PENDING) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Checks if there are any pending requests from Student to FYP Coordinator 
     * to change the title of their allocated project.
     * @return {@code true} if there are any pending requests. 
     * Otherwise, {@code false} if there are no pending requests.
     */
    //to see if there is any request for FYPCoor from Student to change title
    public static boolean anyPendingStudentChangingTitle() {
    	//this is to check whether someone request Li Fang to change title
    	ArrayList<StudentToSupervisor> suprequests = RequestController.getSupRequests();
    	for (StudentToSupervisor request : suprequests) {
    		if (RequestController.checkSupervisor(request.getRequestID()).equals("ASFLI") && RequestController.checkRequestStatus(request.getRequestID()) == RequestStatus.PENDING)
    			return true;
		}
    	return false;
    }
    /**
     * Checks if there are any pending requests from Faculty to FYP Coordinator.
     * @return {@code true} if there are any pending requests. 
     * Otherwise, {@code false} if there are no pending requests.
     */
    //to see if there is any request for FYPCoor from Faculty
    public static boolean anyPendingSupervisorToFYPCoor() {
    	for(Request request : Database.FacultyREQUESTS.values()) {
    		if(request.getRequestStatus() == RequestStatus.PENDING) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Checks if there are any pending requests from Student to Faculty.
     * @return {@code true} if there are any pending requests. 
     * Otherwise, {@code false} if there are no pending requests.
     */
    //to see if there is any request for FYPCoor from Student
    public static boolean anyPendingStudentToSupervisor() {
    	for(Request request : Database.stu_to_sup_REQUESTS.values()) {
    		if(request.getRequestStatus() == RequestStatus.PENDING) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Checks the status of a request.
     * @param requestID The request ID of the request.
     * @return The status of the request.
     */
    public static RequestStatus checkRequestStatus(int requestID) {
    	return Database.REQUESTS.get(requestID).getRequestStatus();
    }
    /**
     * Checks the type of a request.
     * @param requestID The request ID of the request.
     * @return The type of the request.
     */
    public static RequestType checkRequestType(int requestID) {
    	return Database.REQUESTS.get(requestID).getRequestType();
    }
    /**
     * Gets the user ID of the Supervisor corresponding to a specific request ID.
     * @param requestID The request ID of the request.
     * @return The user ID of the supervisor corresponding to the request.
     */
    //check the receiving supervisor of the request sent by students
    public static String checkSupervisor(int requestID) {
    	int projectID = Database.REQUESTS.get(requestID).getRequestedProjectID();
    	Project project = Database.PROJECTS.get(projectID);
    	return project.getSupervisor().getUserID();
    }
    /**
     * Checks if a request from Student to Supervisor exists with the given request ID.
     * @param requestID The request ID of the request to be found.
     * @return {@code true} if the request exists. 
     * Otherwise, {@code false} if the request does not exist.
     */
    //check if requstId exists in the StudentToSupervisor requests
    public static boolean checkRequestIDExistsStudToSup(int requestID) {
    	if (Database.stu_to_sup_REQUESTS.containsKey(requestID))
    		return true;
    	else
    		return false;
    }
    /**
     * Gets the new title of an allocated proposed by the student.
     * @param requestID The request ID of the request.
     * @return The new title of the project.
     */
    //check the new title proposed by student
    public static String checkNewTitleProposed(int requestID) {
    	StudentToSupervisor request = Database.stu_to_sup_REQUESTS.get(requestID);
    	return request.getNewTitle();
    }
    /**
     * Creates a new request to select a project from Student to FYP Coordinator. 
     * Changes the status of the requesting student to REGISTERING.
     * @param projectID The project ID of the project requested.
     * @param studentID The user ID of the student making the request.
     */
    public static void createSelectProjectRequest(int projectID, String studentID) {
    	StudentToFYPCoor request = new StudentToFYPCoor(projectID, "SELECTING", studentID, "PENDING", false);
    	Student student = Database.STUDENTS.get(studentID);
		addStudentToFYPCoorRequest(request, projectID);
		System.out.println("Request to select projectID " + projectID + " sent.");       
		StudentController.updateStudentStatus(studentID, StudentStatus.REGISTERING);
    }
    /**
     * Creates a new request to deregister from a project from Student to FYP Coordinator.
     * @param studentID The user ID of the student making the request.
     */
    public static void createDeregisterRequest(String studentID) {
    	Student student = Database.STUDENTS.get(studentID);
//    	int projectID = student.getProject().getProjectID();
    	int projectID = student.getProjectID();
    	StudentToFYPCoor request = new StudentToFYPCoor(projectID,"DEREGISTERING",  studentID, "PENDING", false);
		addStudentToFYPCoorRequest(request, projectID);
		System.out.println("Request to deregister projectID " + projectID + " sent.");       
    }
    /**
     * Creates a new request to transfer a project from Supervisor to FYP Coordinator.
     * @param projectID The project ID of the project being transferred.
     * @param supervisorID The user ID of the supervisor making the request.
     * @param newSupervisorID The user ID of the supervisor being transferred to.
     */
    public static void createTransferRequest(int projectID, String supervisorID, String newSupervisorID) {
    	FacultyToFYPCoor request = new FacultyToFYPCoor(projectID, supervisorID, newSupervisorID, "PENDING", false); 
		RequestController.addSupervisorRequest(request); 
		System.out.println("Request to transfer project (projectID - " + projectID + ") to supervisor (supervisorID - " + newSupervisorID + ") sent.");
    }
    /**
     * Changes the status of a request with the given request ID.
     * @param requestID The request ID of the request.
     * @param newStatus The new status of the request.
     */
    public static void updateRequestStatus(int requestID, RequestStatus newStatus) {
    	Request request = Database.REQUESTS.get(requestID);
    	request.setRequestStatus(newStatus);
    }
    
    /**
     * Checks if a Student has any changes in their request status in request history.
     * @param studentID The user ID of the student.
     * @return {@code true} if there are any changed requests. 
     * Otherwise, {@code false} if there are no changed requests.
     */
    //to get student view any changes in Request History
    public static boolean anyChangedRequest(String studentID) {
    	for(Request request:Database.REQUESTS.values()) {
    		if(request.getRequestStatus() != RequestStatus.PENDING && request.getCreator()==Database.STUDENTS.get(studentID) && !request.getisViewed()) {
        		return true;
        	}
    	}
    	return false;
    }
    /**
     * Checks if a Faculty has any changes in their request status in request history.
     * @param supervisorID The user ID of the faculty.
     * @return {@code true} if there are any changed requests. 
     * Otherwise, {@code false} if there are no changed requests.
     */
    //to get faculty view any changes in Request History
    public static boolean anyFacultyChangedRequest(String supervisorID) {
    	for(Request request:Database.REQUESTS.values()) {
    		if(request.getRequestStatus() != RequestStatus.PENDING && request.getCreator()==Database.LOGINFACULTIES.get(supervisorID) && !request.getisViewed()) {
        		return true;
        	}
    	}
    	return false;
    }
    
    /**
     * Displays a Student's request history.
     * @param studentID The user ID of the student.
     */
    public static void printStudentRequestHistory(String studentID) {
		ArrayList<Request> historyRequest = RequestController.getMyRequests(studentID);   
		
		if(historyRequest.isEmpty()) {
			System.out.println("There are no requests made by you.");
		} else {
			for(Request request:Database.REQUESTS.values()) {
				if (request.getCreator().getUserID() == studentID)
					request.setViewed(true);
			}
			System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Your Request History                                                                                        ║");
    		System.out.println("╠═════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
    		int i = historyRequest.size();
    		for (Request request : historyRequest)
    		{
    			System.out.printf("║ RequestID           ║ %-85s ║%n", request.getRequestID());
    			System.out.printf("║ Request Status      ║ %-85s ║%n", request.getRequestStatus());
    			System.out.printf("║ Requested ProjectID ║ %-85s ║%n", request.getRequestedProjectID());
    			System.out.printf("║ Action              ║ %-85s ║%n", request.getRequestType());
    			if (request instanceof StudentToSupervisor) {
	    			System.out.printf("║ Original Title      ║ %-85s ║%n", ((StudentToSupervisor)request).getOrigTitle());
	    			System.out.printf("║ Requested Title     ║ %-85s ║%n", ((StudentToSupervisor)request).getNewTitle());
    			}
    			i--;
    			if (i!=0)
    				System.out.println("╠═════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
                
    		}
            System.out.println("╚═════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
        }
    }
}
