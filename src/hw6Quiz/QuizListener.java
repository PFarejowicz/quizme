package hw6Quiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class QuizListener
 *
 */
@WebListener
public class QuizListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public QuizListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	ServletContext context = arg0.getServletContext();
    	DBConnection connection = (DBConnection) context.getAttribute("connection");
    	
    	// close the connection to the database
    	connection.close();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	DBConnection connection = new DBConnection();
    	UserManager userManager = new UserManager(connection.getConnection());
    	QuizManager quizManager = new QuizManager(connection.getConnection());
    	MessageManager messageManager = new MessageManager(connection.getConnection());
    	FriendsManager friendsManager = new FriendsManager(connection.getConnection());
    	
        ServletContext context = arg0.getServletContext();
        
        // store the catalog and connection for use between all users
        context.setAttribute("connection", connection);
        context.setAttribute("user manager", userManager);
        context.setAttribute("quiz manager", quizManager);
        context.setAttribute("message manager", messageManager);
        context.setAttribute("friends manager", friendsManager);
    }
	
}
