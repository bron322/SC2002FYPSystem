package view;
import java.util.ArrayList;
import java.util.Scanner;

import Assignment.Database;
import controller.ProjectController;
import controller.RequestController;
import controller.StudentController;
import enums.ProjectStatus;
import enums.RequestStatus;
import enums.RequestType;
import enums.StudentStatus;
import enums.UserType;
import helper.OptionChecker;
import model.Project;
import model.Request;
import model.Student;
import model.StudentToFYPCoor;
import model.StudentToSupervisor;

public class StudentView extends UserView {
	private StudentController studentController;
	private String studentID;
	private int requestedProjectID;
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	Scanner scanner = new Scanner(System.in);
    
    public StudentView(String studentID) {
    	super();
    	this.studentID = studentID;
        studentController = new StudentController();
    }
    
    @Override
    public void displayMenu() {

		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                 Student Menu                ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ Please choose an option:                    ║");
        System.out.println("║ 0. Log Out                                  ║");
        System.out.println("║ 1. Change Password                          ║");
        System.out.println("║ 2. View Available Projects                  ║");
        System.out.println("║ 3. Select Project                           ║");
        if(ProjectController.anyApprovedProject(studentID)&& !StudentController.checkViewProjectStatus(studentID)) {
        	System.out.println("║ 4. View My Registered Project " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "       ║");
        }
        else {
        	System.out.println("║ 4. View My Registered Project               ║");
        }
        if(RequestController.anyChangedRequest(studentID)) {
        	System.out.println("║ 5. View Request History " + ANSI_RED + "(*NEW*)" + ANSI_RESET + "             ║");
        }
        else {
        	System.out.println("║ 5. View Request History                     ║");
        }       
        System.out.println("║ 6. Request to Change Title                  ║"); 
        System.out.println("║ 7. Deregister Project                       ║");
    	System.out.println("╚═════════════════════════════════════════════╝");
        System.out.printf("Choice: ");
        
    }
    
    @Override
    public void displayApp() {
    	int choice;
    	do
    	{	
	    	displayMenu();
	        choice = OptionChecker.intOptionReturn(0,7) ;
	    	System.out.println();
	        LoginView login = new LoginView(UserType.STUDENT);
	        
	        switch(choice) {
		        case 1:
		        	if (studentController.changePassword(studentID)==0)
		        	{
		        		login.displayMenu();
		        		return;
		        	}
		            break;
	            case 2: 
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║           View Available Projects           ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	displayProject();
	                break;
	            case 3:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║                Select Project               ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	                selectProject();
	                break;
	            case 4:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║          Display Registered Project         ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	displayRegisteredProject();	
	                break;
	            case 5:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║           Display Request History           ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	RequestController.printStudentRequestHistory(studentID);
	                break;
	            case 6:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║                 Change Title                ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	changeTitle();
	                break;
	            case 7:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║             Deregister Project              ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	deregisterProject();
	            	break;
	            case 0:
	            	System.out.println("╔═════════════════════════════════════════════╗");
	                System.out.println("║               Logging out...                ║");
	        		System.out.println("╚═════════════════════════════════════════════╝");
	            	login.displayMenu();
	                break;
	        }
	        System.out.println();
    	}while (choice !=0);
    	
    }
    
    private void displayProject() {
    	if(StudentController.checkStudentStatus(studentID) == StudentStatus.REGISTERED){
    		System.out.println("You are currently allocated to a project.");
    		System.out.println("You are not allowed to view any projects.");
    	}
    	else if (StudentController.checkStudentStatus(studentID) == StudentStatus.DEREGISTERED) {
    		System.out.println("You have been deregistered from your project.");
    		System.out.println("You are not allowed to view any projects.");
    	}
    	else if (StudentController.checkStudentStatus(studentID) == StudentStatus.REGISTERING) {
    		System.out.println("You are currently requesting for a project.");
    		System.out.println("You are not allowed to view any projects.");
    	}
    	else {
    		ArrayList<Project> projectList = ProjectController.getProjectByStatus(ProjectStatus.AVAILABLE);
    		if(projectList.isEmpty())
                System.out.println("There are currently no available projects.");
    		else
    			ProjectController.printProjectList(projectList);
    	}
    }
    
    private void selectProject() {
    	if (StudentController.checkStudentStatus(studentID) == StudentStatus.REGISTERED) {
    		System.out.println("You are currently allocated to a project.");
    		System.out.println("You are not allowed to select any projects.");
    	}
    	else if (StudentController.checkStudentStatus(studentID) == StudentStatus.DEREGISTERED) {
    		System.out.println("You have been deregistered from your project.");
    		System.out.println("You are not allowed to select any projects.");
    	}
    	else if (StudentController.checkStudentStatus(studentID) == StudentStatus.REGISTERING) {
    		System.out.println("You are currently requesting for a project.");
    		System.out.println("You are not allowed to select any projects.");
    	}
    	else {
    		ArrayList<Project> projectList = ProjectController.getProjectByStatus(ProjectStatus.AVAILABLE);
    		if(projectList.isEmpty()) {
                System.out.println("There are currently no available projects.");
                return;
    		}
    		ProjectController.printProjectList(projectList);
    		System.out.print("Enter projectID to request: ");
    		while (true)
            {
    			this.requestedProjectID = OptionChecker.intOnlyOptionReturn();
            	if (!Database.PROJECTS.containsKey(requestedProjectID)) {
                	System.out.println("\nThis projectID does not exist!");
                    System.out.printf("Re-enter choice: ");
                }
            	else
            		break;
            }
    		if (ProjectController.checkProjectStatus(requestedProjectID) != ProjectStatus.AVAILABLE) {
    			System.out.println("Project with ID " + requestedProjectID + " is not available.");
    		}
    		else {
    			RequestController.createSelectProjectRequest(requestedProjectID, studentID);
    		}
    	}
    }
    
    private void updateTitle() {
		ProjectController.printStudentRegisteredProject(studentID);
		System.out.print("Enter new title: ");
		String newTitle = scanner.nextLine();
		System.out.println();
		ArrayList<String> allProjectsTitle = ProjectController.getAllProjectsTitle();
		while (allProjectsTitle.contains(newTitle))
		{
        	System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║ New title is the same as an existing title! ║");
        	System.out.println("╠═════════════════════════════════════════════╣");
        	System.out.println("║ Y. Re-enter new title                       ║");
        	System.out.println("║ N. Return to Student Menu                   ║");
        	System.out.println("╚═════════════════════════════════════════════╝");
        	System.out.printf("Choice: ");
			
			String choice = OptionChecker.ynOptionReturn();
			if (choice.equals("n"))
				return;
			else if (choice.equals("y"))
			{
				System.out.printf("\nPlease re-enter your new title: ");
				newTitle = scanner.nextLine();
			}
		}
		int projectID = ProjectController.getStudentRegisteredProject(studentID);
		StudentToSupervisor request = new StudentToSupervisor(projectID, newTitle, studentID, "PENDING", Database.PROJECTS.get(projectID).getProjectTitle(), false);
		RequestController.addStudentToSupRequest(request);
		System.out.println("Your request to change title has been sent successfully.");
    }
    
    private void displayRegisteredProject() {
    	if (StudentController.checkStudentStatus(studentID)==StudentStatus.DEREGISTERED) {
    		System.out.println("You have been deregistered from your project.");
    	}
    	else if(StudentController.checkStudentStatus(studentID)!=StudentStatus.REGISTERED) {
    		System.out.println("You have not been allocated a project.");
    	}
    	else {
    		ProjectController.printStudentRegisteredProject(studentID);
    		StudentController.updateViewProjectStatus(studentID);
    	}  
    }

    private void changeTitle() {
    	if (StudentController.checkStudentStatus(studentID) == StudentStatus.DEREGISTERED) {
    		System.out.println("You have been deregistered from your project.");
    	}
    	else if (StudentController.checkStudentStatus(studentID) != StudentStatus.REGISTERED) {
    		System.out.println("You have not been allocated a project.");
    	}
    	else {
    		updateTitle();
    	}
    }
    
    private void deregisterProject() {
    	if (StudentController.checkStudentStatus(studentID)==StudentStatus.DEREGISTERED) {
    		System.out.println("You have already been deregistered from your project.");
    		return;
    	}
    	else if (StudentController.checkStudentStatus(studentID)!=StudentStatus.REGISTERED) {
    		System.out.println("You have not been allocated a project.");
    		return;
    	}
    	else if (StudentController.checkStudentStatus(studentID)==StudentStatus.REGISTERED)
    	{
    		ArrayList<Request> myRequests = RequestController.getMyRequests(studentID);   
    		for (Request request : myRequests)
    		{
    			if (request.getRequestType()==RequestType.DEREGISTERING && request.getRequestStatus() == RequestStatus.PENDING)
    			{
    				System.out.println("You have already requested to deregister your project.");
    				System.out.println("Please wait for that request to be approved/rejected.");
    	    		return;
    			}
    		}
    	}
    	
        System.out.println("You are deregistering the following project:\n");
        ProjectController.printStudentRegisteredProject(studentID);
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║            Confirm Deregistration           ║");
       	System.out.println("╠═════════════════════════════════════════════╣");
       	System.out.println("║ Y. Confirm                                  ║");
       	System.out.println("║ N. Return to Student Menu                   ║");
      	System.out.println("╚═════════════════════════════════════════════╝");
       	System.out.print("Choice: ");
   		String option = OptionChecker.ynOptionReturn();
    	if (option.equals("y")) {
    		RequestController.createDeregisterRequest(studentID);
    	}
    }
    
}
