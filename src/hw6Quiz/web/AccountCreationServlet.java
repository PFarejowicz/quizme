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
 * Servlet implementation class AccountCreationServlet
 */
@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountCreationServlet() {
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
		
		// pull the name and password from the form data
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		
		if (userManager.containsUser(email)) {
			// redirect to already used name page because the manager is already storing the name
			RequestDispatcher dispatch = request.getRequestDispatcher("used_account.jsp");
			dispatch.forward(request, response);
		} else if(userManager.hasUsers()){
			// switch to welcome page because account was created
			userManager.addUser(email, password, name);
			session.setAttribute("user id", userManager.getIDByEmail(email));
			session.setAttribute("email", email);
			RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
			dispatch.forward(request, response);
		} else{
			userManager.addAdmin(email, password, name);
			session.setAttribute("user id", userManager.getIDByEmail(email));
			session.setAttribute("email", email);
			RequestDispatcher dispatch = request.getRequestDispatcher("admin_homepage.jsp");
			dispatch.forward(request, response);
		}

	}

}
