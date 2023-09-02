package view;
import java.util.ArrayList;
import java.util.Scanner;

import Assignment.Database;
import controller.FacultyController;
import controller.ProjectController;
import controller.RequestController;
import enums.ProjectStatus;
import enums.RequestStatus;
import enums.UserType;
import helper.OptionChecker;
import model.Faculty;
import model.FacultyToFYPCoor;
import model.Project;
import model.Request;
import model.StudentToSupervisor;

public class FacultyView extends UserView{
	private FacultyController facultyController;
	private String supervisorID;
	private Scanner scanner = new Scanner(System.in);
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

	public FacultyView(String supervisorID) {
        super();
        this.supervisorID = supervisorID;
        facultyController = new FacultyController();
    }
	
	@Override
    public void displayMenu() {
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                 Faculty Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ Please choose an option:                    ║");
        System.out.println("║ 0. Log Out                                  ║");
        System.out.println("║ 1. Change password                          ║");
        if(ProjectController.anyAllocatedProject(supervisorID)) 
        	System.out.println("║ 2. Create/Update/View Project " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "       ║");
        else 
        	System.out.println("║ 2. Create/Update/View Project               ║");
        if(RequestController.anyPendingStudentToSupervisor()) 
        	System.out.println("║ 3. View Student Requests " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "            ║");
        else 
        	System.out.println("║ 3. View Student Requests                    ║");
        if(RequestController.anyFacultyChangedRequest(supervisorID)) 
        	System.out.println("║ 4. View Request History " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "             ║");
        else 
        	System.out.println("║ 4. View Request History                     ║");
        System.out.println("║ 5. Request to Transfer Student              ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
        System.out.printf("Choice: ");
    }
    
	@Override
    public void displayApp() {
    	int choice;
    	do
    	{
    		displayMenu();
    		choice=OptionChecker.intOptionReturn(0,5);
	    	System.out.println();
	        LoginView login = new LoginView(UserType.FACULTY);
	        
	        switch(choice) {
	            case 1:
	            	if (facultyController.changePassword(supervisorID)==0)
	            	{
		        		login.displayMenu();
		        		return;
		        	}
		            break;
	            case 2:
	            	manageProjectView();
	                break;
	            case 3:
	            	displayStudentRequest();
	    			break;
	            case 4:
	    			displayRequestHistory();
	            	break;
	            case 5:
	            	transferStudentView();
	            	break;
	            case 0:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║               Logging out...                ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	login.displayMenu();
	                break;
	        }
	        System.out.println();
    	} while (choice!=0);

    }
	
	public void manageProjectView() {
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                 Project Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
    	System.out.println("║ 1. Create Project                           ║");
    	System.out.println("║ 2. Update Project                           ║");
    	if(ProjectController.anyAllocatedProject(supervisorID)) 
        	System.out.println("║ 3. View My Projects " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "                 ║");
    	else
    		System.out.println("║ 3. View My Projects                         ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
		System.out.print("Choice: ");
		
		int option = OptionChecker.intOptionReturn(1,3);
    	
    	switch(option) {
    	case 1:
    		createProjectView();
    		break;
    	case 2:
    		updateProjectView();
    		break;
    	case 3:
    		System.out.println();
    		ProjectController.printProjectForFaculty(supervisorID);
    		break;
    	}
	}
	
	public void createProjectView()
	{
		System.out.print("\nPlease enter your project title: ");
		String newproject = scanner.nextLine();
		ArrayList<String> allProjectsTitle = ProjectController.getAllProjectsTitle();
		while (allProjectsTitle.contains(newproject))
		{
			System.out.print("\nThis project title already exists\n");
			System.out.print("Please enter another project title: ");
			newproject = scanner.nextLine();
		}
		ProjectController.createProject(newproject, supervisorID);
		System.out.print("\nYour project has been created.\n");
	}
	
	public void updateProjectView()
	{
		System.out.println();
		ProjectController.printProjectForFaculty(supervisorID);
		if(ProjectController.getSupervisorProjects(supervisorID).isEmpty()) {
			System.out.print(" ");
		}
		else {
			System.out.println();
			System.out.print("Enter projectID that you want to update the title of: ");
			int requestedprojectID;
			while (true)
	        {
	        	requestedprojectID = OptionChecker.intOnlyOptionReturn();
	        	if (!ProjectController.getSupervisorProjectsID(supervisorID).contains(requestedprojectID))
	        	{
	        		System.out.println("\nThis projectID does not exist in your list of projects.");
	                System.out.printf("Re-enter projectID: ");
	        	}
	        	else
	        		break;
	        }    	
	        
	        System.out.print("\nEnter your new Project Title: ");
	        String newtitle = scanner.nextLine();
	        
	        ArrayList<String> allProjectsTitle = ProjectController.getAllProjectsTitle();
			while (allProjectsTitle.contains(newtitle))
			{
				System.out.println();
				System.out.println("This project title already exists");
				System.out.print("Please enter another project title: ");
				newtitle = scanner.nextLine();
			}
	        ProjectController.updateProjectTitle(requestedprojectID, newtitle);
	        System.out.print("\nYou have successfully updated your project.\n");
		}	
	}
	
	public void displayStudentRequest() {
    	ArrayList<StudentToSupervisor> suprequests = RequestController.getSupRequests();
    	ArrayList<StudentToSupervisor> pendingsupRequests = new ArrayList<StudentToSupervisor>();
    	for (StudentToSupervisor request : suprequests) {
    		if (RequestController.checkSupervisor(request.getRequestID()).equals(supervisorID) && RequestController.checkRequestStatus(request.getRequestID()) == RequestStatus.PENDING)
    			pendingsupRequests.add(request);
		}
    	if (pendingsupRequests.isEmpty())
    	{
    		
    		System.out.println("There are no pending requests for you");
    	}
    	else
    	{
    		System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Student Requests                                                                                                 ║");
    		System.out.println("╠══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
    		int i = pendingsupRequests.size();
    		for (StudentToSupervisor request : pendingsupRequests)
    		{
    			System.out.printf("║ RequestID                ║ %-85s ║%n", request.getRequestID());
    			System.out.printf("║ Sender ID                ║ %-85s ║%n", request.getCreator().getUserID());
    			System.out.printf("║ Requested ProjectID      ║ %-85s ║%n", request.getProject().getProjectID());
    			System.out.printf("║ Original Title           ║ %-85s ║%n", request.getProject().getProjectTitle());
    			System.out.printf("║ Requested Title          ║ %-85s ║%n", request.getNewTitle());
    			System.out.printf("║ Request Status           ║ %-85s ║%n", request.getRequestStatus());
    			i--;
    			if (i!=0)
    				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
                
    		}
            System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
            ApproveProjectMenu();	
    	}
	}
	
    public void ApproveProjectMenu()
    {
    	System.out.print("Enter requestID: ");
    	int selectedrequestID;
    	while (true)
    	{
    		selectedrequestID = OptionChecker.intOnlyOptionReturn();
    		if(!RequestController.checkRequestIDExistsStudToSup(selectedrequestID))
    		{
    			System.out.println("This request ID does not exist");
    			System.out.print("Re-enter request ID: ");
    		}
    		else if (RequestController.checkRequestStatus(selectedrequestID) != RequestStatus.PENDING)
    		{
    			System.out.println("This request has been approved or rejected");
    			System.out.print("Re-enter request ID: ");
    		}
    		else
    			break;
    	}
    	System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                Approval Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
    	System.out.println("║ 1. Approve Project Title Change             ║");
    	System.out.println("║ 2. Reject Project Title Change              ║");
    	System.out.println("║ 0. Return to Faculty Menu                   ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
    	System.out.print("Choice: ");
    	
        int option = OptionChecker.intOptionReturn(0, 2);
        switch (option)
        {
        	case 1:
        		RequestController.updateRequestStatus(selectedrequestID, RequestStatus.APPROVED);
           		ProjectController.updateProjectTitle(selectedrequestID, Database.REQUESTS.get(selectedrequestID).getRequestedProjectID(), RequestController.checkNewTitleProposed(selectedrequestID));
           		Database.REQUESTS.get(selectedrequestID).setViewed(false);
           		System.out.println("Your project title has been changed.");
           		break;
        	case 2:
        		RequestController.updateRequestStatus(selectedrequestID, RequestStatus.REJECTED);
        		Database.REQUESTS.get(selectedrequestID).setViewed(false);
        		break;
        	case 0:
        		break;
        }
    }
    
    //case 4
    public void displayRequestHistory() {
    	//this is a list of requests from student to specific supervisor
    	ArrayList<StudentToSupervisor> allStudRequests = RequestController.getSupRequests();
    	//this is a list of requests from specific supervisor to fypcoor
    	ArrayList<FacultyToFYPCoor> allFacRequests = RequestController.getFacultyRequests();

        int i =0;
        StudentToSupervisor req; 
        Project proj;
        String supID;
		while(i<allStudRequests.size()) {
			req = allStudRequests.get(i);
			proj = req.getProject();
			supID = proj.getSupervisor().getUserID();
			if(!supID.equals(supervisorID)) {
				allStudRequests.remove(i);
			}
			else 
				i++;
		}
		
		if (allStudRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ You have no incoming requests history.                                                                           ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Incoming Requests History                                                                                        ║");
    		System.out.println("╠══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
    		i = allStudRequests.size();
    		for (StudentToSupervisor request : allStudRequests)
    		{
    			System.out.printf("║ RequestID                ║ %-85s ║%n", request.getRequestID());
    			System.out.printf("║ Sender ID                ║ %-85s ║%n", request.getCreator().getUserID());
    			System.out.printf("║ Requested ProjectID      ║ %-85s ║%n", request.getProject().getProjectID());
    			System.out.printf("║ Original Title           ║ %-85s ║%n", request.getOrigTitle());
    			System.out.printf("║ Requested Title          ║ %-85s ║%n", request.getNewTitle());
    			System.out.printf("║ Request Status           ║ %-85s ║%n", request.getRequestStatus());
    			i--;
    			if (i!=0)
    				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
    		}
    		System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
		}
		
		i=0;
		FacultyToFYPCoor req2;
		while(i<allFacRequests.size()) {
			req2 = allFacRequests.get(i);
			supID = req2.getCreator().getUserID();
			if(!supID.equals(supervisorID)) {
				allFacRequests.remove(i);
			}
			else 
				i++;
		}
		
		if (allFacRequests.size()==0)
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ You have no outgoing requests history.                                                                           ║");
    		System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
		}
		else
		{
			System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    		System.out.println("║ Outgoing Requests History                                                                                        ║");
    		System.out.println("╠══════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╣");
    		i = allFacRequests.size();
    		for (FacultyToFYPCoor request : allFacRequests)
    		{
    			if(request.getRequestStatus()!= RequestStatus.PENDING) {
    				request.setViewed(true);
    			}
    			System.out.printf("║ RequestID                ║ %-85s ║%n", request.getRequestID());
    			System.out.printf("║ Requested ProjectID      ║ %-85s ║%n", request.getRequestedProjectID());
    			System.out.printf("║ Replacement SupervisorID ║ %-85s ║%n", request.getTransferring());
    			System.out.printf("║ Request Status           ║ %-85s ║%n", request.getRequestStatus());
    			i--;
    			if (i!=0)
    				System.out.println("╠══════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");
    		}
    		System.out.println("╚══════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
		}
    }
    
    
    public void transferStudentView() 
    { 
    	if (!FacultyController.isSupervising(supervisorID)) { 
    		System.out.println("You are currently not supervising any projects."); 
    		return; 
    	} 
    	ProjectController.printProjectForFaculty(supervisorID, ProjectStatus.ALLOCATED); 
    	System.out.print("Enter projectID to request transfer: "); 
    	int duplicate; 
    	int requestedprojectID; 
    	while (true) 
    	{ 
    		duplicate = 0; 
    		requestedprojectID = OptionChecker.intOnlyOptionReturn(); 
    		for (Request request : RequestController.getMyRequests(supervisorID)) 
    		{ 
    			if (request.getRequestedProjectID() == requestedprojectID && request.getRequestStatus() == RequestStatus.PENDING) 
    			{ 
    				System.out.println("╔═════════════════════════════════════════════╗");
                    System.out.println("║ A transfer request for this project has     ║");
                    System.out.println("║ already been sent.                          ║");
                	System.out.println("╠═════════════════════════════════════════════╣");
                	System.out.println("║ 1. Re-enter projectID                       ║");
                	System.out.println("║ 2. Return to Faculty Menu                   ║");
                	System.out.println("╚═════════════════════════════════════════════╝");
    				System.out.printf("Choice: ");
    				int choice = OptionChecker.intOptionReturn(1, 2);
    				if (choice == 2)
    					return;
    				duplicate = 1; 
    				break; 
    			} 

    		} 
    		if (duplicate == 1) 
    		{
    			System.out.print("Re-enter projectID: ");
    			continue; 
    		}
    		if (!ProjectController.getSupervisorProjectsID(supervisorID, ProjectStatus.ALLOCATED).contains(requestedprojectID)) 
    		{ 
            	System.out.println("╔═════════════════════════════════════════════╗");
                System.out.println("║ This projectID does not exist in your list  ║");
                System.out.println("║ of supervised projects.                     ║");
            	System.out.println("╠═════════════════════════════════════════════╣");
            	System.out.println("║ 1. Re-enter projectID                       ║");
            	System.out.println("║ 2. Return to Faculty Menu                   ║");
            	System.out.println("╚═════════════════════════════════════════════╝");
				System.out.printf("Choice: ");
				int choice = OptionChecker.intOptionReturn(1, 2);
				if (choice == 2)
					return;
    		} 
    		else 
    			break; 
    	}
    	
    	printSupervisorList(supervisorID); 
    	System.out.printf("\nEnter supervisorID to request transferring to: "); 
    	String transferringSupervisorID = scanner.next(); 
    	scanner.nextLine(); 

    	while (true)
    	{
    		System.out.println(); 
    		if(!FacultyController.checkFacultyExists(transferringSupervisorID)) 
	    		System.out.println("This Supervisor ID does not exist"); 
    		else if(transferringSupervisorID.equals(supervisorID)) 
	    		System.out.println("You cannot transfer to yourself!"); 
    		else
    			break;
    		System.out.printf("Please re-enter Supervisor ID: "); 
    		transferringSupervisorID = scanner.nextLine();  
    	}
    	RequestController.createTransferRequest(requestedprojectID, supervisorID, transferringSupervisorID);
    }
    /**
     * Prints the list of Supervisors according to their supervisor ID.
     * @param supervisorID The user ID of the supervisor.
     */
    public static void printSupervisorList(String supervisorID) 
	{
		int i=0;
		System.out.println();
		System.out.println("╔═════════════════╦═══════════════════════════╦═════════════════╦═══════════════════════════╗");
		System.out.printf("║ %-15s ║ %-25s ║ %-15s ║ %-25s ║%n", "Supervisor ID", "Supervisor Name", "Supervisor ID", "Supervisor Name");
		System.out.println("╠═════════════════╬═══════════════════════════╬═════════════════╬═══════════════════════════╣");
    	
		for(Faculty faculty: Database.LOGINFACULTIES.values()) {
	    	if(!faculty.getUserID().equals(supervisorID)) {
	    		System.out.printf("║ %-15s ║ %-25s ", faculty.getUserID(), faculty.getName());	
	    		i++;
	    	}
			if (i%2==0)
				System.out.printf("║%n");
			if (i==Database.LOGINFACULTIES.size())
				System.out.printf("║ %-15s ║ %-25s ║%n");
	    }
		System.out.println("╚═════════════════╩═══════════════════════════╩═════════════════╩═══════════════════════════╝");
	}
}
