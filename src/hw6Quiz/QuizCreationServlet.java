package hw6Quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizCreationServlet
 */
@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizCreationServlet() {
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
		UserManager manager = (UserManager) getServletContext().getAttribute("user manager");
		String user_id = (String) request.getSession().getAttribute("user id"); 
		
		// pull the name and password from the form data
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Boolean random_order = request.getParameter("random_order").equals("Yes");
		Boolean multiple_pages = request.getParameter("multiple_pages").equals("Yes");
		Boolean immediate_correction = request.getParameter("immediate_correction").equals("Yes");
		
		RequestDispatcher dispatch = request.getRequestDispatcher("add_question.jsp");
		dispatcher.forward(request, response); 
	}
}
