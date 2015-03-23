package hw6Quiz.web;

import hw6Quiz.manager.UserManager;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		UserManager userManager = (UserManager) context.getAttribute("user manager");
		
		// pull the name and password from the creation servlet
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (userManager.containsUser(email) && userManager.checkPassword(email, password)) {
			// redirect to welcome page if login was successful
			session.setAttribute("user id", userManager.getIDByEmail(email));
			session.setAttribute("email", email);
			if(userManager.isAdmin(email)){
				RequestDispatcher dispatch = request.getRequestDispatcher("admin_homepage.jsp");
				dispatch.forward(request, response);
			} else{
				RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
				dispatch.forward(request, response);
			}
		} else {
			// redirect to invalid page if user name or password do not match or are wrong
			RequestDispatcher dispatch = request.getRequestDispatcher("invalid_account.jsp");
			dispatch.forward(request, response);
		}
	}
	
}
