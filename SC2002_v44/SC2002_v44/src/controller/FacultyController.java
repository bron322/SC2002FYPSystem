package controller;

import Assignment.Database;
import model.Faculty;
import view.FacultyView;
import view.FYPCoordinatorView;
import view.LoginView;

/** 
 * A controller class that acts as a "middle man" between the
 * view classes {@link FacultyView}, {@link FYPCoordinatorView}, 
 * {@link LoginView} and the model class {@link Faculty}. <p>
 * 
 * It can filter and authenticate {@link Faculty} details.
 * @author Chung Zhi Xuan
 * @version 1.0
 * @since 2023-04-09
 */

public class FacultyController extends UserController<Faculty>{
	/**
     * Creates a new FacultyController.
     */
    public FacultyController() {
    	
    }
    
    /**
     * Authenticates the Faculty's password with their user ID.
     * @param userID The faculty's user ID.
     * @param password The password entered by the faculty.
     * @return This Faculty if authentication is successful. 
     * Otherwise, return {@code null}.
     */
    public Faculty authenticate(String userID, String password) {
        if(Database.LOGINFACULTIES.containsKey(userID)) {
        	Faculty faculty = Database.LOGINFACULTIES.get(userID);
            if(faculty.getPassword().equals(password)) {
                return faculty; //authentication success
            }
        }
        return null; //authentication fail
    }
    
    /**
     * Checks if a specific Faculty exists.
     * @param supervisorID The user ID of a supervisor.
     * @return {@code true} if the faculty exists. Otherwise, {@code false} if the faculty does not exist.
     */
    public static boolean checkFacultyExists(String supervisorID) {
    	if (Database.LOGINFACULTIES.containsKey(supervisorID))
    		return true;
    	else
    		return false;
    }    
    
    /**
     * Checks if a Supervisor is supervising any projects.
     * @param supervisorID The user ID of the supervisor.
     * @return {@code true} if the faculty is supervising one or more projects. 
     * Otherwise, {@code false} if the faculty is not supervising any projects.
     */
    public static boolean isSupervising(String supervisorID) {
    	Faculty faculty = Database.LOGINFACULTIES.get(supervisorID);
    	if (faculty.getNumSupervised() > 0)
    		return true;
    	else
    		return false;
    }
    
}
