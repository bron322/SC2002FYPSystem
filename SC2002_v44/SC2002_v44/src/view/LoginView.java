package view;

import Assignment.Database;
import controller.FYPCoordinatorController;
import controller.FacultyController;
import controller.StudentController;
import enums.ExcelType;
import enums.UserType;
import helper.Excel;
import helper.OptionChecker;
import model.FYPCoordinator;
import model.Faculty;
import model.Student;

import java.util.*;

public class LoginView{

	private StudentController studentController;
	private FacultyController facultyController;
	private String userType;
	private Student student;
	private Faculty faculty;
	private UserType type;

	public LoginView(UserType type) {
		studentController = new StudentController();
		facultyController = new FacultyController();
	}
	
	public void displayMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║                  Main Menu                  ║");
    	System.out.println("╠═════════════════════════════════════════════╣");
		System.out.println("║ Please select domain:                       ║");
        System.out.println("║ Enter 's' for student                       ║");
        System.out.println("║ Enter 'f' for faculty                       ║");
        System.out.println("║ Enter 'e' to exit portal                    ║");
		System.out.println("╚═════════════════════════════════════════════╝");
        System.out.printf("Selection: ");
        
        userType = scanner.nextLine();
        while (!(userType.equals("s") || userType.equals("f") || userType.equals("e")))
        {
        	System.out.println("\nInvalid choice.");
        	System.out.printf("Re-enter Domain: ");
        	userType = scanner.nextLine();
        }
        
        System.out.println();
        
        if (userType.equals("e"))
        {
        	System.out.println("╔═════════════════════════════════════════════╗");
        	System.out.println("║            Exiting the portal...            ║");
        	System.out.println("╚═════════════════════════════════════════════╝");
        	Database.exportDataBase();
        	return;
        }
        else if(userType.equals("s")) {
    		type = UserType.STUDENT;
    		System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║            Entered Student Domain           ║");
    		System.out.println("╚═════════════════════════════════════════════╝");
        	System.out.print("Enter your user ID: ");
    		String userID = scanner.nextLine();
    		while  (!Database.STUDENTS.containsKey(userID)){
            	System.out.println("\n╔═════════════════════════════════════════════╗");
                System.out.println("║ User is not found in Student Domain!        ║");
            	System.out.println("╠═════════════════════════════════════════════╣");
            	System.out.println("║ Y. Re-enter User ID                         ║");
            	System.out.println("║ N. Return to Main Menu                      ║");
            	System.out.println("╚═════════════════════════════════════════════╝");
    			System.out.printf("Choice: ");
    			
    			String choice = OptionChecker.ynOptionReturn();
    			if (choice.equals("n"))
    			{
    				System.out.println();
    				displayMenu();
    				return;
    			}
    			else if (choice.equals("y"))
    			{
    				System.out.printf("\nPlease re-enter your user ID: ");
        			userID = scanner.nextLine();
    			}
    			
        	}
    		System.out.printf("Enter your password: ");
    		String password = scanner.nextLine().trim();
			student = studentController.authenticate(userID, password);
			while (student == null)
			{
				System.out.printf("\nPassword is wrong!\n");
				System.out.printf("Please re-enter your password: ");
				password = scanner.nextLine().trim();
				student = studentController.authenticate(userID, password);
			}
	        System.out.println("\nLogin Successful! Welcome, " + student.getName() + "!");
			StudentView studentView = new StudentView(student.getUserID());
			studentView.displayApp();
    		
        }
        
        else if(userType.equals("f")) {
    		type = UserType.FACULTY;
    		System.out.println("╔═════════════════════════════════════════════╗");
        	System.out.println("║           Entered Faculty Domain            ║");
    		System.out.println("╚═════════════════════════════════════════════╝");
        	System.out.print("Enter your user ID: ");
    		String userID = scanner.nextLine();
    		while  (!Database.LOGINFACULTIES.containsKey(userID)){
            	System.out.println("\n╔═════════════════════════════════════════════╗");
                System.out.println("║ User is not found in Faculty Domain!        ║");
            	System.out.println("╠═════════════════════════════════════════════╣");
            	System.out.println("║ Y. Re-enter User ID                         ║");
            	System.out.println("║ N. Return to Main Menu                      ║");
            	System.out.println("╚═════════════════════════════════════════════╝");
    			System.out.printf("Choice: ");
    			
    			String choice = OptionChecker.ynOptionReturn();
    			if (choice.equals("n"))
    			{
    				System.out.println();
    				displayMenu();
    				return;
    			}
    			else if (choice.equals("y"))
    			{
    				System.out.printf("\nPlease re-enter your user ID: ");
        			userID = scanner.nextLine();
    			}
    			
        	}
    		
    		System.out.printf("Enter your password: ");
    		String password = scanner.nextLine().trim();
    		faculty = facultyController.authenticate(userID, password);
			while (faculty == null)
			{
				System.out.printf("\nPassword is wrong!\n");
				System.out.printf("Please re-enter your password: ");
				password = scanner.nextLine().trim();
				faculty = facultyController.authenticate(userID, password);
			}

			System.out.println("\nLogin Successful! Welcome, " + faculty.getName() + "!");
			if(faculty.getUserID().equals("ASFLI")) {
				type = UserType.FYPCOOR;
				FYPCoordinatorView coorView = new FYPCoordinatorView(faculty.getUserID());
				coorView.displayApp();
			}
			else {
				FacultyView facultyView = new FacultyView(faculty.getUserID());
				facultyView.displayApp();
			}
        }
	}
}
